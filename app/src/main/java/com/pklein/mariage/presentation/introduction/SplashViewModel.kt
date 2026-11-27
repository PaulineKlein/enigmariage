package com.pklein.mariage.presentation.introduction

import androidx.lifecycle.ViewModel
import com.pklein.mariage.data.model.GameNames
import com.pklein.mariage.data.model.Trophies
import com.pklein.mariage.data.repository.PlayerRepository
import com.pklein.mariage.data.repository.RoomFirebaseRepository
import com.pklein.mariage.data.repository.TrophyRepository
import com.pklein.mariage.presentation.introduction.screen.TrophyProgress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

sealed interface SplashConfirmation {
    /** A new game would replace the saved one. */
    data class ReplaceGame(
        val roomCode: String,
        val names: GameNames,
    ) : SplashConfirmation

    /** The solo game becomes the game of room [roomCode]. */
    data class JoinRoom(
        val roomCode: String,
        val names: GameNames,
    ) : SplashConfirmation

    /** The game moves from room [from] to room [to]. */
    data class SwitchRoom(
        val from: String,
        val to: String,
        val names: GameNames,
    ) : SplashConfirmation

    /** The game goes back to solo. */
    data class LeaveRoom(
        val roomCode: String,
    ) : SplashConfirmation
}

data class SplashUiState(
    val hasSavedGame: Boolean,
    /** The room of the saved game, null for a solo game or without a saved game. */
    val roomCode: String?,
    val trophyProgress: TrophyProgress,
    val confirmation: SplashConfirmation? = null,
    /** True once a new game is set up and the introduction must open; call [SplashViewModel.onIntroLaunched] after. */
    val launchIntro: Boolean = false,
)

/**
 * The rules of the home screen: what happens to the saved game when starting a new game, joining or leaving a room.
 * An action that would change a saved game first puts a [SplashConfirmation] in the state; [confirm] applies it.
 */
@HiltViewModel
class SplashViewModel
    @Inject
    constructor(
        private val playerRepository: PlayerRepository,
        private val trophyRepository: TrophyRepository,
        private val roomFirebaseRepository: RoomFirebaseRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(readState())
        val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

        val page: String?
            get() = playerRepository.page

        /** Re-reads the saved game and the trophies, keeping a pending confirmation. */
        fun refresh() {
            _uiState.update {
                readState().copy(
                    confirmation = it.confirmation,
                    launchIntro = it.launchIntro,
                )
            }
        }

        fun onNewGame() = startNewGame(roomCode = "", names = GameNames())

        /**
         * Creating or joining a room. With a saved game, [keepProgress] brings it into the room; otherwise the room
         * starts a new game, which replaces the saved one.
         */
        fun onMultiplayer(
            roomCode: String,
            names: GameNames,
            keepProgress: Boolean,
        ) {
            val state = _uiState.value
            val current = state.roomCode
            when {
                !state.hasSavedGame || !keepProgress -> startNewGame(roomCode, names)
                roomCode == current -> joinWithSavedGame(roomCode, names)
                current == null -> ask(SplashConfirmation.JoinRoom(roomCode, names))
                else -> ask(SplashConfirmation.SwitchRoom(current, roomCode, names))
            }
        }

        fun onLeaveRoom() {
            _uiState.value.roomCode?.let { ask(SplashConfirmation.LeaveRoom(it)) }
        }

        /** Applies the pending confirmation. */
        fun confirm() {
            val confirmation = _uiState.value.confirmation ?: return
            _uiState.update { it.copy(confirmation = null) }
            when (confirmation) {
                is SplashConfirmation.ReplaceGame -> {
                    playerRepository.resetStorage()
                    storeRoom(confirmation.roomCode, confirmation.names)
                    _uiState.update { it.copy(launchIntro = true) }
                }

                is SplashConfirmation.JoinRoom -> {
                    joinWithSavedGame(
                        confirmation.roomCode,
                        confirmation.names,
                    )
                }

                is SplashConfirmation.SwitchRoom -> {
                    joinWithSavedGame(
                        confirmation.to,
                        confirmation.names,
                    )
                }

                is SplashConfirmation.LeaveRoom -> {
                    playerRepository.leaveRoom()
                    refresh()
                }
            }
        }

        fun dismiss() {
            _uiState.update { it.copy(confirmation = null) }
        }

        fun onIntroLaunched() {
            _uiState.update { it.copy(launchIntro = false) }
        }

        suspend fun createRoom(
            roomCode: String,
            names: GameNames,
        ) = roomFirebaseRepository.createRoom(roomCode, names)

        suspend fun joinRoom(roomCode: String) = roomFirebaseRepository.joinRoom(roomCode)

        // An empty room code means a solo game.
        private fun startNewGame(
            roomCode: String,
            names: GameNames,
        ) {
            if (_uiState.value.hasSavedGame) {
                ask(SplashConfirmation.ReplaceGame(roomCode, names))
            } else {
                storeRoom(roomCode, names)
                _uiState.update { it.copy(launchIntro = true) }
            }
        }

        // The saved game becomes the room's game: only the room and its names change, the progress is untouched.
        private fun joinWithSavedGame(
            roomCode: String,
            names: GameNames,
        ) {
            storeRoom(roomCode, names)
            refresh()
        }

        private fun ask(confirmation: SplashConfirmation) {
            _uiState.update { it.copy(confirmation = confirmation) }
        }

        private fun storeRoom(
            roomCode: String,
            names: GameNames,
        ) {
            playerRepository.storeRoomCode(roomCode)
            playerRepository.storeGameNames(names)
        }

        private fun readState(): SplashUiState {
            val hasSavedGame = !playerRepository.page.isNullOrEmpty()
            return SplashUiState(
                hasSavedGame = hasSavedGame,
                roomCode = if (hasSavedGame) playerRepository.roomCode?.takeIf { it.isNotBlank() } else null,
                trophyProgress =
                    TrophyProgress(
                        unlocked = trophyRepository.getAllTrophies().count { it.second },
                        total = Trophies.entries.size,
                    ),
            )
        }
    }
