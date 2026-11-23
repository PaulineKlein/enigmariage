package com.pklein.mariage.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Source
import com.pklein.mariage.data.model.RoomPlayer
import com.pklein.mariage.data.model.ranked
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.time.Duration.Companion.milliseconds

private const val ROOMS = "rooms"
private const val PLAYERS = "players"
private const val CREATORS = "creators"
private const val NETWORK_TIMEOUT_MS = 8_000L
private const val CREATE_INTERVAL_MS = 60_000L

enum class RoomResult {
    OK,
    NOT_FOUND,

    /** No internet or a timeout: multiplayer cannot start. */
    UNAVAILABLE,

    /** This device already created a room within the last minute. */
    RATE_LIMITED,
}

@Singleton
class RoomFirebaseRepository
    @Inject
    constructor() {
        /**
         * Writes the room document, so that [joinRoom] can find it; needs the server, not just the local cache.
         * The rules only accept it together with a fresh `lastRoomAt` on the host's own `creators` document, which
         * limits an account to one room per minute. Asking again for a room this device already created is fine.
         */
        suspend fun createRoom(roomCode: String): RoomResult =
            online {
                val uid = signIn()
                val db = FirebaseFirestore.getInstance()
                val room = roomDocument(roomCode)
                val creator = db.collection(CREATORS).document(uid)

                val existing = room.get(Source.SERVER).awaitResult()
                if (existing.exists()) {
                    // Someone else's room means a code clash, which is as good as unavailable.
                    return@online if (existing.getString("ownerUid") == uid) RoomResult.OK else RoomResult.UNAVAILABLE
                }

                val lastRoomAt = creator.get(Source.SERVER).awaitResult().getTimestamp("lastRoomAt")
                if (lastRoomAt != null && System.currentTimeMillis() - lastRoomAt.toDate().time < CREATE_INTERVAL_MS) {
                    return@online RoomResult.RATE_LIMITED
                }

                db
                    .batch()
                    .set(room, mapOf("createdAt" to FieldValue.serverTimestamp(), "ownerUid" to uid))
                    .set(creator, mapOf("lastRoomAt" to FieldValue.serverTimestamp()))
                    .commit()
                    .awaitResult()
                RoomResult.OK
            }

        /** Checks on the server that a host created [roomCode], so a mistyped code is caught before the game starts. */
        suspend fun joinRoom(roomCode: String): RoomResult =
            online {
                signIn()
                if (roomDocument(roomCode).get(Source.SERVER).awaitResult().exists()) {
                    RoomResult.OK
                } else {
                    RoomResult.NOT_FOUND
                }
            }

        private suspend fun online(block: suspend () -> RoomResult): RoomResult =
            try {
                withTimeoutOrNull(NETWORK_TIMEOUT_MS.milliseconds) { block() } ?: RoomResult.UNAVAILABLE
            } catch (cancellation: CancellationException) {
                throw cancellation
            } catch (_: Exception) {
                RoomResult.UNAVAILABLE
            }

        private suspend fun signIn(): String {
            val auth = FirebaseAuth.getInstance()
            auth.currentUser?.let { return it.uid }
            return auth
                .signInAnonymously()
                .awaitResult()
                .user
                ?.uid
                ?: throw IllegalStateException("Anonymous sign-in returned no user")
        }

        private fun roomDocument(roomCode: String): DocumentReference = FirebaseFirestore.getInstance().collection(ROOMS).document(roomCode)

        private suspend fun <T> Task<T>.awaitResult(): T =
            suspendCancellableCoroutine { continuation ->
                addOnSuccessListener { continuation.resume(it) }
                addOnFailureListener { continuation.resumeWithException(it) }
            }

        /**
         * Signs in anonymously, saves [me] in the room (one document per device, so playing again
         * overwrites the previous score), then emits the ranked players of the room on every change.
         */
        fun submitAndObserve(
            roomCode: String,
            me: RoomPlayer,
        ): Flow<List<RoomPlayer>> =
            callbackFlow {
                var registration: ListenerRegistration? = null

                fun start(uid: String) {
                    val players =
                        FirebaseFirestore
                            .getInstance()
                            .collection(ROOMS)
                            .document(roomCode)
                            .collection(PLAYERS)

                    // Offline, this write stays queued and is sent later; only a refusal fails it.
                    players
                        .document(uid)
                        .set(
                            mapOf(
                                "name" to me.name,
                                "gender" to me.gender,
                                "score" to me.score,
                                "timeMillis" to me.timeMillis,
                                "trophies" to me.trophies,
                            ),
                        ).addOnFailureListener { close(it) }

                    registration =
                        players.addSnapshotListener { snapshot, error ->
                            if (error != null) {
                                close(error)
                            } else if (snapshot != null) {
                                trySend(snapshot.documents.mapNotNull { it.toRoomPlayer(uid) }.ranked())
                            }
                        }
                }

                val auth = FirebaseAuth.getInstance()
                val user = auth.currentUser
                if (user != null) {
                    start(user.uid)
                } else {
                    auth
                        .signInAnonymously()
                        .addOnSuccessListener { result ->
                            result.user?.let { start(it.uid) }
                                ?: close(IllegalStateException("Anonymous sign-in returned no user"))
                        }.addOnFailureListener { close(it) }
                }

                awaitClose { registration?.remove() }
            }

        private fun DocumentSnapshot.toRoomPlayer(myId: String): RoomPlayer? =
            RoomPlayer(
                id = id,
                name = getString("name") ?: return null,
                gender = getString("gender"),
                score = getLong("score")?.toInt() ?: return null,
                timeMillis = getLong("timeMillis") ?: 0L,
                trophies = getLong("trophies")?.toInt() ?: 0,
                isMe = id == myId,
            )
    }
