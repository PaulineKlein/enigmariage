package com.pklein.mariage.ui.atome

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.pklein.mariage.presentation.destinations.italie.data.ItalieTwoPuzzle
import com.pklein.mariage.presentation.salleReception.screen.data.ReceptionFourPuzzle
import com.pklein.mariage.ui.theme.ColorAccent
import com.pklein.mariage.ui.theme.ColorBleuFonce
import kotlinx.coroutines.launch

private const val STICK_THICKNESS = 7f

private val WoodColor = Color(0xFFE0A84E)
private val HeadColor = Color(0xFFC0392B)
private val ShadowColor = Color(0x33000000)
private val RestingShadow = Offset(2f, 3f)
private val LiftedShadow = Offset(4f, 7f)

@Composable
fun MatchstickPuzzle(
    state: MatchstickPuzzleState,
    description: String,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()

    Canvas(
        modifier =
            modifier
                .fillMaxWidth()
                .aspectRatio(state.config.width / state.config.height)
                .semantics { contentDescription = description }
                .pointerInput(state) {
                    awaitEachGesture {
                        val down = awaitFirstDown()
                        val unit = size.width / state.config.width
                        if (!state.startDrag(down.position / unit)) return@awaitEachGesture
                        down.consume()
                        drag(down.id) { change ->
                            state.dragBy(change.positionChange() / unit)
                            change.consume()
                        }
                        scope.launch { state.endDrag() }
                    }
                },
    ) {
        scale(scale = size.width / state.config.width, pivot = Offset.Zero) {
            drawPuzzle(state)
        }
    }
}

private fun DrawScope.drawPuzzle(state: MatchstickPuzzleState) {
    val dragged = state.dragged

    if (dragged != null) {
        val taken =
            state.sticks
                .filter { it !== dragged }
                .map { it.slot }
                .toSet()
        val target = state.snapTarget()
        state.config.slots.forEachIndexed { index, slot ->
            if (index in taken) return@forEachIndexed
            val color =
                if (index == target) ColorAccent.copy(alpha = 0.6f) else ColorBleuFonce.copy(alpha = 0.12f)
            drawLine(color, slot.head, slot.tail, STICK_THICKNESS + 4f, StrokeCap.Round)
        }
    }

    state.sticks
        .filter { it !== dragged }
        .forEach { drawMatchstick(it.head, it.tail, RestingShadow) }
    dragged?.let { drawMatchstick(it.head, it.tail, LiftedShadow) }
}

private fun DrawScope.drawMatchstick(
    head: Offset,
    tail: Offset,
    shadow: Offset,
) {
    drawLine(ShadowColor, head + shadow, tail + shadow, STICK_THICKNESS, StrokeCap.Round)
    drawLine(WoodColor, head, tail, STICK_THICKNESS, StrokeCap.Round)
    drawCircle(HeadColor, radius = STICK_THICKNESS * 0.85f, center = head)
}

@Preview(showBackground = true)
@Composable
private fun ReceptionFourPuzzlePreview() {
    MatchstickPuzzle(state = MatchstickPuzzleState(ReceptionFourPuzzle), description = "")
}

@Preview(showBackground = true)
@Composable
private fun ItalieTwoPuzzlePreview() {
    MatchstickPuzzle(state = MatchstickPuzzleState(ItalieTwoPuzzle), description = "")
}
