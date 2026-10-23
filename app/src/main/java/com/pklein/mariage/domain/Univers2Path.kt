package com.pklein.mariage.domain

import com.pklein.mariage.data.model.SharedPreferenceKey
import com.pklein.mariage.data.model.Trophies
import com.pklein.mariage.presentation.GameStep

data class Univers2Path(
    val mainUnivers: SharedPreferenceKey,
    val firstStep: GameStep,
    val subUnivers: List<SharedPreferenceKey>,
    val subUniversName: List<Int>,
    val subUniversFirstStep: List<GameStep>,
    val subUniversIvMapmonde: List<Int>,
    val completeTrophy: Trophies,
)
