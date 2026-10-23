package com.pklein.mariage.presentation.combatFinal

import com.pklein.mariage.R
import com.pklein.mariage.ui.atome.LogigrammeCategory
import com.pklein.mariage.ui.atome.LogigrammeWord

internal object CombatFinalDogPuzzle {
    private const val UPPERCUT = 0
    private const val CROCHET = 1
    private const val BALAYAGE = 2
    private const val RAPIDE = 3
    private const val MOYEN = 4
    private const val LENT = 5
    private const val TETE = 6
    private const val VENTRE = 7
    private const val JAMBES = 8

    val clue = R.string.combatFinal_dog_clue

    val rowLabels =
        listOf(
            R.string.combatFinal_dog_uppercut,
            R.string.combatFinal_dog_crochet,
            R.string.combatFinal_dog_balayage,
            R.string.combatFinal_dog_rapide,
            R.string.combatFinal_dog_moyen,
            R.string.combatFinal_dog_lent,
        )

    val columnLabels =
        listOf(
            R.string.combatFinal_dog_tete,
            R.string.combatFinal_dog_ventre,
            R.string.combatFinal_dog_jambes,
            R.string.combatFinal_dog_rapide,
            R.string.combatFinal_dog_moyen,
            R.string.combatFinal_dog_lent,
        )

    // Listed by category, 3 per row: the pool displays them in this order.
    val words =
        listOf(
            LogigrammeWord(UPPERCUT, R.string.combatFinal_dog_uppercut, LogigrammeCategory.COUP),
            LogigrammeWord(CROCHET, R.string.combatFinal_dog_crochet, LogigrammeCategory.COUP),
            LogigrammeWord(BALAYAGE, R.string.combatFinal_dog_balayage, LogigrammeCategory.COUP),
            LogigrammeWord(RAPIDE, R.string.combatFinal_dog_rapide, LogigrammeCategory.VITESSE),
            LogigrammeWord(MOYEN, R.string.combatFinal_dog_moyen, LogigrammeCategory.VITESSE),
            LogigrammeWord(LENT, R.string.combatFinal_dog_lent, LogigrammeCategory.VITESSE),
            LogigrammeWord(TETE, R.string.combatFinal_dog_tete, LogigrammeCategory.ZONE),
            LogigrammeWord(VENTRE, R.string.combatFinal_dog_ventre, LogigrammeCategory.ZONE),
            LogigrammeWord(JAMBES, R.string.combatFinal_dog_jambes, LogigrammeCategory.ZONE),
        )

    val solution =
        setOf(
            setOf(UPPERCUT, RAPIDE, JAMBES),
            setOf(CROCHET, LENT, TETE),
            setOf(BALAYAGE, MOYEN, VENTRE),
        )
}
