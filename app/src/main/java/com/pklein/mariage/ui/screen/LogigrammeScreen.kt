package com.pklein.mariage.ui.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pklein.mariage.R
import com.pklein.mariage.ui.atome.AppButton
import com.pklein.mariage.ui.atome.HelpCard
import com.pklein.mariage.ui.atome.LogigrammeCategory
import com.pklein.mariage.ui.atome.LogigrammeGrid
import com.pklein.mariage.ui.atome.LogigrammeState
import com.pklein.mariage.ui.atome.LogigrammeWord
import com.pklein.mariage.ui.atome.ParcheminCard
import com.pklein.mariage.ui.atome.WordArrangement
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.ColorHint
import com.pklein.mariage.ui.theme.DefaultTextStyles

@Composable
fun LogigrammeScreen(
    @StringRes title: Int,
    clue: Int,
    question: AnnotatedString,
    help: AnnotatedString,
    rowLabels: List<Int>,
    columnLabels: List<Int>,
    words: List<LogigrammeWord>,
    state: LogigrammeState,
    onHomeClick: () -> Unit,
    onValidateClick: (isSolved: Boolean) -> Unit,
) {
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

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = question,
                    style = DefaultTextStyles.body,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(16.dp))

                ParcheminCard(
                    text = stringResource(id = clue),
                    modifier =
                        Modifier
                            .widthIn(max = 600.dp)
                            .align(Alignment.CenterHorizontally),
                )

                Spacer(modifier = Modifier.height(16.dp))

                LogigrammeGrid(
                    rowLabels = rowLabels.map { stringResource(id = it) },
                    columnLabels = columnLabels.map { stringResource(id = it) },
                    cells = state.cells,
                    onCellClick = state::toggleCell,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .widthIn(max = 600.dp)
                            .align(Alignment.CenterHorizontally),
                )

                Spacer(modifier = Modifier.height(16.dp))

                HelpCard(text = help)

                Spacer(modifier = Modifier.height(24.dp))

                WordArrangement(
                    words = words,
                    state = state,
                    modifier =
                        Modifier
                            .widthIn(max = 600.dp)
                            .align(Alignment.CenterHorizontally),
                )

                TextButton(
                    onClick = state::reset,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                ) {
                    Text(
                        text = stringResource(id = R.string.recommencer),
                        style = DefaultTextStyles.body.copy(color = ColorHint),
                    )
                }
            }

            AppButton(
                text = stringResource(id = R.string.valider),
                onClick = { onValidateClick(state.isSolved) },
                modifier =
                    Modifier
                        .fillMaxWidth(0.7f)
                        .padding(top = 24.dp)
                        .align(Alignment.CenterHorizontally),
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 900)
@Composable
private fun LogigrammeScreenPreview() {
    LogigrammeScreen(
        title = R.string.combatFinal_titre,
        clue = R.string.combatFinal_dog_clue,
        question = AnnotatedString(stringResource(R.string.combatFinal_dog_question)),
        help = AnnotatedString(stringResource(R.string.combatFinal_dog_help)),
        rowLabels =
            listOf(
                R.string.combatFinal_dog_uppercut,
                R.string.combatFinal_dog_crochet,
                R.string.combatFinal_dog_balayage,
                R.string.combatFinal_dog_rapide,
                R.string.combatFinal_dog_moyen,
                R.string.combatFinal_dog_lent,
            ),
        columnLabels =
            listOf(
                R.string.combatFinal_dog_tete,
                R.string.combatFinal_dog_ventre,
                R.string.combatFinal_dog_jambes,
                R.string.combatFinal_dog_rapide,
                R.string.combatFinal_dog_moyen,
                R.string.combatFinal_dog_lent,
            ),
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
        onHomeClick = {},
        onValidateClick = {},
    )
}
