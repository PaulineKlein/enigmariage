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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pklein.mariage.R
import com.pklein.mariage.presentation.destinations.italie.data.ItalieTwoPuzzle
import com.pklein.mariage.presentation.salleReception.screen.data.ReceptionFourPuzzle
import com.pklein.mariage.ui.atome.AppButton
import com.pklein.mariage.ui.atome.MatchstickPuzzle
import com.pklein.mariage.ui.atome.MatchstickPuzzleState
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.ColorHint
import com.pklein.mariage.ui.theme.DefaultTextStyles
import kotlinx.coroutines.launch

@Composable
fun MatchstickPuzzleScreen(
    @StringRes title: Int,
    message: AnnotatedString,
    state: MatchstickPuzzleState,
    onHomeClick: () -> Unit,
    onValidateClick: (isSolved: Boolean) -> Unit,
    @StringRes puzzleDescription: Int = R.string.reception_four_puzzle_description,
) {
    val scope = rememberCoroutineScope()

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

                Spacer(modifier = Modifier.height(44.dp))

                Text(
                    text = message,
                    style = DefaultTextStyles.body,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(24.dp))

                MatchstickPuzzle(
                    state = state,
                    description = stringResource(id = puzzleDescription),
                    modifier =
                        Modifier
                            .widthIn(max = 600.dp)
                            .align(Alignment.CenterHorizontally),
                )

                TextButton(
                    onClick = { scope.launch { state.reset() } },
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

@Preview(showBackground = true)
@Composable
private fun ReceptionFourPuzzleScreenPreview() {
    MatchstickPuzzleScreen(
        title = R.string.reception_titre,
        message = AnnotatedString("Déplace une seule allumette pour que l'égalité soit juste."),
        state = MatchstickPuzzleState(ReceptionFourPuzzle),
        onHomeClick = {},
        onValidateClick = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun ItalieTwoPuzzleScreenPreview() {
    MatchstickPuzzleScreen(
        title = R.string.diner_titre_italie,
        message = AnnotatedString("Déplace une seule allumette pour que l'égalité soit juste."),
        state = MatchstickPuzzleState(ItalieTwoPuzzle),
        onHomeClick = {},
        onValidateClick = {},
    )
}
