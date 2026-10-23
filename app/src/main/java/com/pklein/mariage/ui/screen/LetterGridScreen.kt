package com.pklein.mariage.ui.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pklein.mariage.R
import com.pklein.mariage.ui.atome.AppButton
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.ColorAccent
import com.pklein.mariage.ui.theme.ColorParchemin
import com.pklein.mariage.ui.theme.ColorParcheminText
import com.pklein.mariage.ui.theme.DefaultTextStyles

private const val GRID_SIZE = 6

val DefaultLetterGrid: List<List<Char>> =
    listOf(
        "SEAPIR",
        "ASCOCO",
        "BMOULE",
        "LDURES",
        "EAPROE",
        "SBEIEC",
    ).map { it.toList() }

@Composable
fun LetterGridScreen(
    @StringRes title: Int,
    message: AnnotatedString,
    onHomeClick: () -> Unit,
    onValidateClick: (String) -> Unit,
    grid: List<List<Char>> = DefaultLetterGrid,
) {
    // Cells are stored as row * GRID_SIZE + column, in click order.
    val selectedCells = remember { mutableStateListOf<Int>() }
    val selectedWord = selectedCells.map { grid[it / GRID_SIZE][it % GRID_SIZE] }.joinToString("")

    AppTheme {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .systemBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
        ) {
            Column(
                modifier =
                    Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
            ) {
                MyNavigationBar(
                    title = title,
                    onHomeClick = onHomeClick,
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = message,
                    style = DefaultTextStyles.body,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(24.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    grid.forEachIndexed { rowIndex, row ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            row.forEachIndexed { columnIndex, letter ->
                                val cell = rowIndex * GRID_SIZE + columnIndex
                                LetterCell(
                                    letter = letter,
                                    selected = cell in selectedCells,
                                    onClick = {
                                        if (!selectedCells.remove(cell)) selectedCells.add(cell)
                                    },
                                    modifier = Modifier.weight(1f),
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = selectedWord,
                    style = DefaultTextStyles.sousTitre,
                    textAlign = TextAlign.Center,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .heightIn(min = 28.dp),
                )
            }

            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                AppButton(
                    text = stringResource(id = R.string.valider),
                    onClick = { onValidateClick(selectedWord) },
                    enabled = selectedWord.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth(0.7f),
                )
            }
        }
    }
}

@Composable
private fun LetterCell(
    letter: Char,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(8.dp)
    Box(
        contentAlignment = Alignment.Center,
        modifier =
            modifier
                .aspectRatio(1f)
                .clip(shape)
                .drawBehind {
                    drawRect(if (selected) ColorAccent else ColorParchemin)
                }.clickable(role = Role.Checkbox, onClick = onClick)
                .padding(1.dp),
    ) {
        Text(
            text = letter.toString(),
            style =
                DefaultTextStyles.bodyBig.copy(
                    color = if (selected) Color.Black else ColorParcheminText,
                ),
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(showBackground = true, name = "Grille de lettres")
@Composable
private fun LetterGridScreenPreview() {
    LetterGridScreen(
        title = R.string.diner_titre_caraibes,
        message = AnnotatedString("Trouve le mot caché dans la grille."),
        onHomeClick = {},
        onValidateClick = {},
    )
}
