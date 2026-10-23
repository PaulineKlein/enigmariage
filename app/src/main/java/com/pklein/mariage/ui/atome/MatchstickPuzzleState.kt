package com.pklein.mariage.ui.atome

import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.lerp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.abs

private const val GRAB_RADIUS = 20f
private const val SNAP_RADIUS = 40f
private const val GLIDE_MS = 250
private const val ORIENTATION_WEIGHT = 20f

internal class Segment(
    val head: Offset,
    val tail: Offset,
) {
    val center get() = (head + tail) / 2f
}

// A puzzle lives in a logical width x height space; the canvas scales it to the available width.
internal class MatchstickPuzzleConfig(
    val width: Float,
    val height: Float,
    val slots: List<Segment>,
    val initialSlots: List<Int>,
    // (stick index, slot) moves that lead to the solution, used by showSolution.
    val solution: List<Pair<Int, Int>>,
    val isSolved: (occupied: Set<Int>) -> Boolean,
)

private fun distanceToSegment(
    point: Offset,
    segment: Segment,
): Float {
    val ab = segment.tail - segment.head
    val ap = point - segment.head
    val t = ((ap.x * ab.x + ap.y * ab.y) / (ab.x * ab.x + ab.y * ab.y)).coerceIn(0f, 1f)
    return (point - (segment.head + ab * t)).getDistance()
}

private fun misalignment(
    first: Segment,
    second: Segment,
): Float {
    val a = first.tail - first.head
    val b = second.tail - second.head
    return 1f - abs(a.x * b.x + a.y * b.y) / (a.getDistance() * b.getDistance())
}

@Stable
internal class StickState(
    slot: Int,
    private val slots: List<Segment>,
) {
    var slot by mutableIntStateOf(slot)
    var head by mutableStateOf(slots[slot].head)
    var tail by mutableStateOf(slots[slot].tail)
    var isGliding by mutableStateOf(false)

    val segment get() = Segment(head, tail)

    suspend fun glideTo(target: Int) {
        val fromHead = head
        val fromTail = tail
        val to = slots[target]
        slot = target
        isGliding = true
        try {
            animate(0f, 1f, animationSpec = tween(GLIDE_MS)) { fraction, _ ->
                head = lerp(fromHead, to.head, fraction)
                tail = lerp(fromTail, to.tail, fraction)
            }
        } finally {
            isGliding = false
        }
    }
}

@Stable
class MatchstickPuzzleState internal constructor(
    internal val config: MatchstickPuzzleConfig,
    initialSlots: List<Int> = config.initialSlots,
) {
    internal val sticks = initialSlots.map { StickState(it, config.slots) }
    internal var dragged by mutableStateOf<StickState?>(null)

    private val occupied get() = sticks.map { it.slot }.toSet()

    val isSolved: Boolean
        get() = config.isSolved(occupied)

    internal fun startDrag(point: Offset): Boolean {
        val stick =
            sticks
                .filter { !it.isGliding }
                .minByOrNull { distanceToSegment(point, it.segment) }
                ?.takeIf { distanceToSegment(point, it.segment) <= GRAB_RADIUS }
                ?: return false
        dragged = stick
        return true
    }

    internal fun dragBy(delta: Offset) {
        dragged?.let {
            it.head += delta
            it.tail += delta
        }
    }

    // Free slot the dragged stick would snap to if released now.
    internal fun snapTarget(): Int? {
        val stick = dragged ?: return null
        val taken = sticks.filter { it !== stick }.map { it.slot }.toSet()
        val center = stick.segment.center
        val slots = config.slots
        // Orientation also counts, to tell apart slots sharing (or nearly sharing) a center, like the bars of an X.
        return slots.indices
            .filter { it !in taken }
            .minByOrNull {
                (slots[it].center - center).getDistance() + ORIENTATION_WEIGHT *
                    misalignment(
                        stick.segment,
                        slots[it],
                    )
            }?.takeIf { (slots[it].center - center).getDistance() <= SNAP_RADIUS }
    }

    internal suspend fun endDrag() {
        val stick = dragged ?: return
        val target = snapTarget() ?: stick.slot
        dragged = null
        stick.glideTo(target)
    }

    suspend fun reset() =
        coroutineScope {
            sticks.forEachIndexed { index, stick ->
                val initial = config.initialSlots[index]
                if (stick.slot != initial) launch { stick.glideTo(initial) }
            }
        }

    suspend fun showSolution() {
        reset()
        coroutineScope {
            config.solution.forEach { (stick, slot) -> launch { sticks[stick].glideTo(slot) } }
        }
    }

    companion object {
        internal fun saver(config: MatchstickPuzzleConfig) =
            listSaver(
                save = { state -> state.sticks.map { it.slot } },
                restore = { MatchstickPuzzleState(config, it) },
            )
    }
}
