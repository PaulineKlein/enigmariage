package com.pklein.mariage.domain

import com.pklein.mariage.data.repository.PlayerGender
import com.pklein.mariage.presentation.GameStep

interface IInspector {
    val gender: PlayerGender
    val nameId: Int
    val avatarResId: Int
    val universPath: Univers2Path

    fun getDescription(step: GameStep?): Int?

    fun getEnigme(step: GameStep?): Int?

    fun getEnigmeAccessibility(step: GameStep?): Int?

    fun getClue(step: GameStep?): Int?

    fun getReponse(step: GameStep?): Int?
}
