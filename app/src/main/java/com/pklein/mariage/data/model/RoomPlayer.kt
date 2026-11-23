package com.pklein.mariage.data.model

const val ROOM_CODE_MAX_LENGTH = 12
const val ROOM_CODE_MIN_LENGTH = 3
private const val GENERATED_CODE_LENGTH = 6

// No 0/O or 1/I, which are easy to confuse when a code is read out loud or off a screen.
private const val GENERATED_CODE_ALPHABET = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789"

fun generateRoomCode(): String = (1..GENERATED_CODE_LENGTH).map { GENERATED_CODE_ALPHABET.random() }.joinToString("")

/** Room codes are shared by voice or on a screen, so keep them to unambiguous upper-case letters and digits. */
fun String.toRoomCode(): String =
    filter { it in '0'..'9' || it in 'a'..'z' || it in 'A'..'Z' }
        .uppercase()
        .take(ROOM_CODE_MAX_LENGTH)

data class RoomPlayer(
    val id: String,
    val name: String,
    val gender: String?,
    val score: Int,
    val timeMillis: Long,
    val trophies: Int = 0,
    val isMe: Boolean = false,
)

/**
 * Best score first, then the most trophies, then the fastest; an unknown time counts as the slowest.
 * Players saved before trophies were sent count as having none.
 */
fun List<RoomPlayer>.ranked(): List<RoomPlayer> =
    sortedWith(
        compareByDescending<RoomPlayer> { it.score }
            .thenByDescending { it.trophies }
            .thenBy { if (it.timeMillis > 0) it.timeMillis else Long.MAX_VALUE },
    )
