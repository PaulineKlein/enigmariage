package com.pklein.mariage.presentation.combatFinal.resultat.model

import com.pklein.mariage.data.model.RoomPlayer

sealed interface PodiumState {
    /** The player did not join a room. */
    data object Hidden : PodiumState

    data object Loading : PodiumState

    data object Error : PodiumState

    data class Ready(
        val roomCode: String,
        val players: List<RoomPlayer>,
    ) : PodiumState
}
