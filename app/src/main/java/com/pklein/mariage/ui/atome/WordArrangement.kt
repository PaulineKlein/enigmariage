package com.pklein.mariage.ui.atome

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.pklein.mariage.R
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.ColorCadenaBackground
import com.pklein.mariage.ui.theme.ColorHelp
import com.pklein.mariage.ui.theme.ColorHint
import com.pklein.mariage.ui.theme.ColorJusCoco
import com.pklein.mariage.ui.theme.DefaultTextStyles
import kotlin.math.roundToInt

private val WORD_HEIGHT = 44.dp
private val WORD_SPACING = 8.dp
private val CHIP_SHAPE = RoundedCornerShape(10.dp)

/**
 * The words sit in a pool, grouped by [LogigrammeWord.category] (3 per row, in the order given),
 * and can be dragged into the slots below. Dragging a placed word out of the slots, or tapping
 * it, puts it back in the pool.
 */
@Composable
fun WordArrangement(
    words: List<LogigrammeWord>,
    state: LogigrammeState,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    var containerCoords by remember { mutableStateOf<LayoutCoordinates?>(null) }
    val slotCoords = remember { arrayOfNulls<LayoutCoordinates>(LogigrammeState.SLOT_COUNT) }

    fun slotAt(pointer: Offset): Int? =
        slotCoords
            .indexOfFirst { coords ->
                coords != null &&
                    coords.isAttached &&
                    Rect(coords.positionInRoot(), coords.size.toSize()).contains(pointer)
            }.takeIf { it >= 0 }

    val onDrop = { state.drop(slotAt(state.dragPointer)) }

    Box(modifier = modifier.onGloballyPositioned { containerCoords = it }) {
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(WORD_SPACING)) {
                words.chunked(LogigrammeState.LINE_SIZE).forEach { poolRow ->
                    Row(horizontalArrangement = Arrangement.spacedBy(WORD_SPACING)) {
                        poolRow.forEach { word ->
                            Box(modifier = Modifier.weight(1f).height(WORD_HEIGHT)) {
                                if (word.id !in state.slots) {
                                    DraggableWord(
                                        word = word,
                                        state = state,
                                        onDrop = onDrop,
                                        modifier = Modifier.fillMaxSize(),
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(WORD_SPACING)) {
                repeat(LogigrammeState.LINE_COUNT) { line ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        repeat(LogigrammeState.LINE_SIZE) { position ->
                            val slot = line * LogigrammeState.LINE_SIZE + position
                            if (position > 0) {
                                Text(
                                    text = "+",
                                    style = DefaultTextStyles.body,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.width(16.dp),
                                )
                            }
                            Box(
                                modifier =
                                    Modifier
                                        .weight(1f)
                                        .height(WORD_HEIGHT)
                                        .onGloballyPositioned { slotCoords[slot] = it }
                                        .background(Color.White.copy(alpha = 0.4f), CHIP_SHAPE)
                                        .border(1.dp, ColorHint.copy(alpha = 0.6f), CHIP_SHAPE),
                            ) {
                                state.slots[slot]?.let { wordId ->
                                    DraggableWord(
                                        word = words.first { it.id == wordId },
                                        state = state,
                                        onDrop = onDrop,
                                        onTap = { state.removeFromSlot(slot) },
                                        modifier = Modifier.fillMaxSize(),
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        state.draggedWord?.let { wordId ->
            WordChip(
                word = words.first { it.id == wordId },
                modifier =
                    Modifier
                        .offset {
                            val origin =
                                containerCoords?.takeIf { it.isAttached }?.positionInRoot()
                                    ?: Offset.Zero
                            val topLeft = state.dragPointer - state.dragGrab - origin
                            IntOffset(topLeft.x.roundToInt(), topLeft.y.roundToInt())
                        }.size(
                            with(density) {
                                DpSize(state.dragSize.width.toDp(), state.dragSize.height.toDp())
                            },
                        ).alpha(0.9f),
            )
        }
    }
}

@Composable
private fun DraggableWord(
    word: LogigrammeWord,
    state: LogigrammeState,
    onDrop: () -> Unit,
    modifier: Modifier = Modifier,
    onTap: (() -> Unit)? = null,
) {
    var coords by remember { mutableStateOf<LayoutCoordinates?>(null) }

    WordChip(
        word = word,
        modifier =
            modifier
                .onGloballyPositioned { coords = it }
                .alpha(if (state.draggedWord == word.id) 0.3f else 1f)
                .pointerInput(word.id) {
                    detectTapGestures(onTap = { onTap?.invoke() })
                }.pointerInput(word.id) {
                    detectDragGestures(
                        onDragStart = { start ->
                            coords?.let {
                                state.startDrag(
                                    word.id,
                                    it.localToRoot(start),
                                    start,
                                    it.size,
                                )
                            }
                        },
                        onDrag = { change, delta ->
                            change.consume()
                            state.dragBy(delta)
                        },
                        onDragEnd = onDrop,
                        onDragCancel = state::cancelDrag,
                    )
                },
    )
}

@Composable
private fun WordChip(
    word: LogigrammeWord,
    modifier: Modifier = Modifier,
) {
    val color =
        when (word.category) {
            LogigrammeCategory.COUP -> ColorJusCoco
            LogigrammeCategory.VITESSE -> ColorCadenaBackground
            LogigrammeCategory.ZONE -> ColorHelp
        }
    Box(
        modifier =
            modifier
                .background(color, CHIP_SHAPE)
                .border(1.dp, ColorHint, CHIP_SHAPE)
                .padding(horizontal = 4.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(id = word.label),
            style = DefaultTextStyles.body.copy(fontSize = 14.sp),
            textAlign = TextAlign.Center,
            maxLines = 1,
        )
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
private fun WordArrangementPreview() {
    AppTheme {
        WordArrangement(
            words =
                listOf(
                    LogigrammeWord(0, R.string.combatFinal_dog_uppercut, LogigrammeCategory.COUP),
                    LogigrammeWord(1, R.string.combatFinal_dog_crochet, LogigrammeCategory.COUP),
                    LogigrammeWord(2, R.string.combatFinal_dog_balayage, LogigrammeCategory.COUP),
                    LogigrammeWord(3, R.string.combatFinal_dog_rapide, LogigrammeCategory.VITESSE),
                    LogigrammeWord(4, R.string.combatFinal_dog_moyen, LogigrammeCategory.VITESSE),
                    LogigrammeWord(5, R.string.combatFinal_dog_lent, LogigrammeCategory.VITESSE),
                    LogigrammeWord(6, R.string.combatFinal_dog_tete, LogigrammeCategory.ZONE),
                    LogigrammeWord(7, R.string.combatFinal_dog_ventre, LogigrammeCategory.ZONE),
                    LogigrammeWord(8, R.string.combatFinal_dog_jambes, LogigrammeCategory.ZONE),
                ),
            state = LogigrammeState(emptySet()),
            modifier = Modifier.padding(16.dp),
        )
    }
}
