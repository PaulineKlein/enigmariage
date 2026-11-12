package com.pklein.mariage.presentation.salleReception.screen.data

import androidx.compose.ui.geometry.Offset
import com.pklein.mariage.ui.atome.MatchstickPuzzleConfig
import com.pklein.mariage.ui.atome.Segment

private const val TOP = 40f
private const val STICK_LENGTH = 60f
private const val V_HALF_WIDTH = 15f
private const val MID_Y = TOP + STICK_LENGTH / 2

// Every place a matchstick can lie. "VI = II + II" uses all of them but I_LEFT.
internal object Slot {
    const val I_LEFT = 0
    const val V_LEFT = 1
    const val V_RIGHT = 2
    const val I_RIGHT = 3
    const val EQUALS_TOP = 4
    const val EQUALS_BOTTOM = 5
    const val B_FIRST = 6
    const val B_SECOND = 7
    const val PLUS_HORIZONTAL = 8
    const val PLUS_VERTICAL = 9
    const val C_FIRST = 10
    const val C_SECOND = 11

    // Third stick of the first right-hand number: "V = III + II".
    const val B_THIRD = 12

    // Slots that lead nowhere: they hide which one is the right one while dragging.
    const val FIRST_DECOY = 13
}

private fun vertical(x: Float) = Segment(Offset(x, TOP), Offset(x, TOP + STICK_LENGTH))

private fun horizontal(
    startX: Float,
    endX: Float,
    y: Float,
) = Segment(Offset(startX, y), Offset(endX, y))

private val RECEPTION_SLOTS: List<Segment> =
    listOf(
        vertical(20f),
        Segment(Offset(50f - V_HALF_WIDTH, TOP), Offset(50f, TOP + STICK_LENGTH)),
        Segment(Offset(50f + V_HALF_WIDTH, TOP), Offset(50f, TOP + STICK_LENGTH)),
        vertical(80f),
        horizontal(100f, 160f, MID_Y - 12f),
        horizontal(100f, 160f, MID_Y + 12f),
        vertical(186f),
        vertical(202f),
        horizontal(222f, 282f, MID_Y),
        vertical(252f),
        vertical(302f),
        vertical(318f),
        vertical(170f),
        horizontal(100f, 160f, MID_Y),
        vertical(50f),
        horizontal(222f, 282f, TOP),
        horizontal(222f, 282f, TOP + STICK_LENGTH),
    )

// VI = II + II
private val INITIAL_SLOTS =
    listOf(
        Slot.V_LEFT,
        Slot.V_RIGHT,
        Slot.I_RIGHT,
        Slot.EQUALS_TOP,
        Slot.EQUALS_BOTTOM,
        Slot.B_FIRST,
        Slot.B_SECOND,
        Slot.PLUS_HORIZONTAL,
        Slot.PLUS_VERTICAL,
        Slot.C_FIRST,
        Slot.C_SECOND,
    )

private fun leftNumber(occupied: Set<Int>): Int? {
    val hasV = Slot.V_LEFT in occupied && Slot.V_RIGHT in occupied
    if ((Slot.V_LEFT in occupied) != (Slot.V_RIGHT in occupied)) return null
    val before = Slot.I_LEFT in occupied
    val after = Slot.I_RIGHT in occupied
    return when {
        hasV && before && !after -> 4
        hasV && !before && !after -> 5
        hasV && !before && after -> 6
        !hasV && (before != after) -> 1
        else -> null
    }
}

private fun sticksCount(
    occupied: Set<Int>,
    vararg slots: Int,
): Int? = slots.count { it in occupied }.takeIf { it > 0 }

// Covers both IV = II + II and V = III + II: only the stick counts differ.
private fun isEquationCorrect(occupied: Set<Int>): Boolean {
    if (Slot.EQUALS_TOP !in occupied || Slot.EQUALS_BOTTOM !in occupied) return false
    if (Slot.PLUS_HORIZONTAL !in occupied) return false
    val left = leftNumber(occupied) ?: return false
    val b = sticksCount(occupied, Slot.B_FIRST, Slot.B_SECOND, Slot.B_THIRD) ?: return false
    val c = sticksCount(occupied, Slot.C_FIRST, Slot.C_SECOND) ?: return false
    val right = if (Slot.PLUS_VERTICAL in occupied) b + c else b - c
    return left == right
}

// The "I" on the right of the V (stick 2) goes on its left: IV = II + II
// Or the "I" on the right of the V (stick 2) goes on the right: V = III + II
internal val ReceptionFourPuzzle =
    MatchstickPuzzleConfig(
        width = 332f,
        height = 140f,
        slots = RECEPTION_SLOTS,
        initialSlots = INITIAL_SLOTS,
        solution = listOf(2 to Slot.I_LEFT),
        isSolved = { occupied ->
            occupied.none { it >= Slot.FIRST_DECOY } &&
                isEquationCorrect(occupied) &&
                occupied.count { it !in INITIAL_SLOTS } == 1
        },
    )
