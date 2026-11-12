package com.pklein.mariage.presentation.destinations.italie.data

import androidx.compose.ui.geometry.Offset
import com.pklein.mariage.ui.atome.MatchstickPuzzleConfig
import com.pklein.mariage.ui.atome.Segment

private const val TOP = 30f
private const val BOTTOM = 70f
private const val MID = 50f
private const val V_HALF_WIDTH = 12f
private const val X_WIDTH = 20f
private const val BAR_LENGTH = 36f
private const val BAR_OFFSET = 7f

private fun vertical(x: Float) = Segment(Offset(x, TOP), Offset(x, BOTTOM))

private fun bar(
    startX: Float,
    y: Float,
) = Segment(Offset(startX, y), Offset(startX + BAR_LENGTH, y))

private fun vLeft(center: Float) = Segment(Offset(center - V_HALF_WIDTH, TOP), Offset(center, BOTTOM))

private fun vRight(center: Float) = Segment(Offset(center + V_HALF_WIDTH, TOP), Offset(center, BOTTOM))

private fun backslash(left: Float) = Segment(Offset(left, TOP), Offset(left + X_WIDTH, BOTTOM))

private fun slash(left: Float) = Segment(Offset(left + X_WIDTH, TOP), Offset(left, BOTTOM))

private object Slot {
    const val V_LEFT = 0
    const val V_RIGHT = 1
    const val I_FIRST = 2
    const val I_SECOND = 3
    const val I_THIRD = 4
    const val FIRST_SIGN_UPPER = 5
    const val FIRST_SIGN_LOWER = 6
    const val I_BEFORE_X = 7
    const val X_BACK = 8
    const val X_FORWARD = 9
    const val SECOND_SIGN_UPPER = 10
    const val SECOND_SIGN_LOWER = 11
    const val LAST_I = 12

    // Slots that lead nowhere: they hide which one is the right one while dragging.
    const val FIRST_DECOY = 13
}

private val SLOTS =
    listOf(
        vLeft(26f),
        vRight(26f),
        vertical(60f),
        vertical(82f),
        vertical(104f),
        bar(126f, MID - BAR_OFFSET),
        bar(126f, MID + BAR_OFFSET),
        vertical(184f),
        backslash(204f),
        slash(204f),
        bar(246f, MID - BAR_OFFSET),
        bar(246f, MID + BAR_OFFSET),
        vertical(304f),
        vertical(26f),
        vertical(144f),
        vertical(172f),
        vertical(214f),
        vertical(264f),
        vertical(326f),
    )

// VIII - IX = I
private val INITIAL_SLOTS =
    listOf(
        Slot.V_LEFT,
        Slot.V_RIGHT,
        Slot.I_FIRST,
        Slot.I_SECOND,
        Slot.I_THIRD,
        Slot.FIRST_SIGN_LOWER,
        Slot.I_BEFORE_X,
        Slot.X_BACK,
        Slot.X_FORWARD,
        Slot.SECOND_SIGN_UPPER,
        Slot.SECOND_SIGN_LOWER,
        Slot.LAST_I,
    )

private val SIGN_SLOTS =
    setOf(
        Slot.FIRST_SIGN_UPPER,
        Slot.FIRST_SIGN_LOWER,
        Slot.SECOND_SIGN_UPPER,
        Slot.SECOND_SIGN_LOWER,
    )

// VIII = IX - I: one bar of the second "=" goes to the first "-" to make an "=", the other one stays as the "-".
private fun isSolved(occupied: Set<Int>): Boolean =
    occupied.none { it >= Slot.FIRST_DECOY } &&
        occupied - SIGN_SLOTS == INITIAL_SLOTS.toSet() - SIGN_SLOTS &&
        Slot.FIRST_SIGN_UPPER in occupied &&
        Slot.FIRST_SIGN_LOWER in occupied &&
        listOf(Slot.SECOND_SIGN_UPPER, Slot.SECOND_SIGN_LOWER).count { it in occupied } == 1

internal val ItalieTwoPuzzle =
    MatchstickPuzzleConfig(
        width = 340f,
        height = 100f,
        slots = SLOTS,
        initialSlots = INITIAL_SLOTS,
        solution = listOf(INITIAL_SLOTS.indexOf(Slot.SECOND_SIGN_UPPER) to Slot.FIRST_SIGN_UPPER),
        isSolved = ::isSolved,
    )
