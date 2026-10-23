package com.pklein.mariage.ui.atome

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pklein.mariage.R
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.ColorErreur
import com.pklein.mariage.ui.theme.ColorGrey
import com.pklein.mariage.ui.theme.ColorHelpText
import com.pklein.mariage.ui.theme.ColorHint
import com.pklein.mariage.ui.theme.DefaultTextStyles

private val ROW_LABEL_WIDTH = 76.dp
private val HEADER_HEIGHT = 56.dp
private val BLOCK_GAP = 4.dp
private val MAX_CELL_SIZE = 48.dp
private const val BLOCK_SIZE = 3

/**
 * Logic grid: rows and columns are listed in blocks of [BLOCK_SIZE]. The bottom-right block
 * crosses a category with itself, so it is greyed out and not clickable.
 */
@Composable
fun LogigrammeGrid(
    rowLabels: List<String>,
    columnLabels: List<String>,
    cells: List<LogigrammeCellState>,
    onCellClick: (row: Int, column: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(modifier = modifier) {
        val cellSize =
            ((maxWidth - ROW_LABEL_WIDTH - BLOCK_GAP) / columnLabels.size).coerceAtMost(
                MAX_CELL_SIZE,
            )

        Column(modifier = Modifier.align(Alignment.Center)) {
            Row {
                Spacer(modifier = Modifier.width(ROW_LABEL_WIDTH))
                columnLabels.forEachIndexed { column, label ->
                    if (column == BLOCK_SIZE) Spacer(modifier = Modifier.width(BLOCK_GAP))
                    ColumnHeader(label = label, width = cellSize)
                }
            }
            rowLabels.forEachIndexed { row, rowLabel ->
                if (row == BLOCK_SIZE) Spacer(modifier = Modifier.height(BLOCK_GAP))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = rowLabel,
                        style = DefaultTextStyles.body.copy(fontSize = 13.sp),
                        textAlign = TextAlign.End,
                        maxLines = 1,
                        modifier = Modifier.width(ROW_LABEL_WIDTH).padding(end = 6.dp),
                    )
                    columnLabels.forEachIndexed { column, columnLabel ->
                        if (column == BLOCK_SIZE) Spacer(modifier = Modifier.width(BLOCK_GAP))
                        GridCell(
                            state = cells[row * columnLabels.size + column],
                            enabled = !(row >= BLOCK_SIZE && column >= BLOCK_SIZE),
                            size = cellSize,
                            label = "$rowLabel, $columnLabel",
                            onClick = { onCellClick(row, column) },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ColumnHeader(
    label: String,
    width: Dp,
) {
    Box(
        modifier = Modifier.size(width = width, height = HEADER_HEIGHT).padding(bottom = 4.dp),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Text(
            text = label,
            style = DefaultTextStyles.body.copy(fontSize = 13.sp),
            maxLines = 1,
            softWrap = false,
            modifier = Modifier.rotateVertically(),
        )
    }
}

// The rotation is purely visual, so the layout must swap width and height itself.
private fun Modifier.rotateVertically(): Modifier =
    layout { measurable, _ ->
        val placeable = measurable.measure(Constraints())
        layout(placeable.height, placeable.width) {
            placeable.place(
                x = -(placeable.width - placeable.height) / 2,
                y = (placeable.width - placeable.height) / 2,
            )
        }
    }.rotate(-90f)

@Composable
private fun GridCell(
    state: LogigrammeCellState,
    enabled: Boolean,
    size: Dp,
    label: String,
    onClick: () -> Unit,
) {
    val stateDescription =
        stringResource(
            when (state) {
                LogigrammeCellState.EMPTY -> R.string.combatFinal_dog_cell_empty
                LogigrammeCellState.TRUE -> R.string.combatFinal_dog_cell_true
                LogigrammeCellState.FALSE -> R.string.combatFinal_dog_cell_false
            },
        )
    Box(
        modifier =
            Modifier
                .size(size)
                .background(if (enabled) Color.White.copy(alpha = 0.6f) else ColorGrey.copy(alpha = 0.6f))
                .border(0.5.dp, ColorHint)
                .then(if (enabled) Modifier.clickable(onClick = onClick) else Modifier)
                .semantics { contentDescription = "$label : $stateDescription" },
    ) {
        if (enabled && state != LogigrammeCellState.EMPTY) {
            Canvas(modifier = Modifier.size(size)) {
                val stroke = 3.dp.toPx()
                if (state == LogigrammeCellState.TRUE) {
                    val start = Offset(this.size.width * 0.22f, this.size.height * 0.55f)
                    val middle = Offset(this.size.width * 0.42f, this.size.height * 0.75f)
                    val end = Offset(this.size.width * 0.8f, this.size.height * 0.28f)
                    drawLine(ColorHelpText, start, middle, stroke, StrokeCap.Round)
                    drawLine(ColorHelpText, middle, end, stroke, StrokeCap.Round)
                } else {
                    val min = this.size.width * 0.28f
                    val max = this.size.width * 0.72f
                    drawLine(
                        ColorErreur,
                        Offset(min, min),
                        Offset(max, max),
                        stroke,
                        StrokeCap.Round,
                    )
                    drawLine(
                        ColorErreur,
                        Offset(max, min),
                        Offset(min, max),
                        stroke,
                        StrokeCap.Round,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
private fun LogigrammeGridPreview() {
    AppTheme {
        LogigrammeGrid(
            rowLabels = listOf("Uppercut", "Crochet", "Balayage", "Rapide", "Moyen", "Lent"),
            columnLabels = listOf("Tête", "Ventre", "Jambes", "Rapide", "Moyen", "Lent"),
            cells =
                List(36) {
                    when (it % 3) {
                        0 -> LogigrammeCellState.TRUE
                        1 -> LogigrammeCellState.FALSE
                        else -> LogigrammeCellState.EMPTY
                    }
                },
            onCellClick = { _, _ -> },
            modifier = Modifier.padding(16.dp),
        )
    }
}
