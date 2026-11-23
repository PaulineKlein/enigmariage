package com.pklein.mariage.presentation.salleCocktails.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pklein.mariage.R
import com.pklein.mariage.ui.atome.AppButton
import com.pklein.mariage.ui.atome.CustomInput
import com.pklein.mariage.ui.modifier.clearFocusOnTap
import com.pklein.mariage.ui.screen.MyNavigationBar
import com.pklein.mariage.ui.screen.TwoPageScreen
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.DefaultTextStyles
import com.pklein.mariage.ui.theme.annotatedStringResource

private val CONTENT_MAX_WIDTH = 480.dp
private val IMAGE_MAX_WIDTH = 300.dp

/** The home bar stays above two swipeable pages: the gourde story, then the riddle with its answer field. */
@Composable
fun CocktailsSixScreen(
    playerName: String,
    onHomeClick: () -> Unit,
    onValidateClick: (String) -> Unit,
) {
    val answerState = rememberTextFieldState()

    TwoPageScreen(
        header = {
            MyNavigationBar(
                title = R.string.cocktail_one_titre,
                onHomeClick = onHomeClick,
            )
        },
        firstPage = { GourdePage(playerName = playerName) },
        secondPage = {
            RiddlePage(
                answerState = answerState,
                onValidateClick = onValidateClick,
            )
        },
    )
}

@Composable
private fun GourdePage(playerName: String) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        if (maxWidth > maxHeight) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier =
                        Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    GourdeMessage1(playerName = playerName)
                    GourdeMessage2()
                }
                Box(
                    modifier = Modifier.weight(1f).fillMaxHeight(),
                    contentAlignment = Alignment.Center,
                ) {
                    GourdeImage(contentScale = ContentScale.Fit, modifier = Modifier.fillMaxSize())
                }
            }
        } else {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .heightIn(min = maxHeight),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                GourdeMessage1(playerName = playerName)
                GourdeImage(
                    contentScale = ContentScale.FillWidth,
                    modifier =
                        Modifier
                            .widthIn(max = IMAGE_MAX_WIDTH)
                            .fillMaxWidth()
                            .padding(vertical = 24.dp),
                )
                GourdeMessage2()
            }
        }
    }
}

@Composable
private fun GourdeMessage1(playerName: String) {
    Text(
        text = annotatedStringResource(R.string.cocktail_six_message_1, playerName),
        style = DefaultTextStyles.bodyBig,
        textAlign = TextAlign.Center,
        modifier = Modifier.widthIn(max = CONTENT_MAX_WIDTH).fillMaxWidth(),
    )
}

@Composable
private fun GourdeMessage2() {
    Text(
        text = annotatedStringResource(R.string.cocktail_six_message_2),
        style = DefaultTextStyles.bodyBig.copy(fontWeight = FontWeight.Bold),
        textAlign = TextAlign.Center,
        modifier = Modifier.widthIn(max = CONTENT_MAX_WIDTH).fillMaxWidth(),
    )
}

@Composable
private fun GourdeImage(
    contentScale: ContentScale,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(R.drawable.image_gourde_rabbit),
        contentDescription = stringResource(R.string.salle_cocktails_gourde_accessibility_rabbit),
        contentScale = contentScale,
        modifier = modifier,
    )
}

@Composable
private fun RiddlePage(
    answerState: TextFieldState,
    onValidateClick: (String) -> Unit,
) {
    val answer = answerState.text.toString()

    Column(modifier = Modifier.fillMaxSize().clearFocusOnTap()) {
        BoxWithConstraints(modifier = Modifier.weight(1f).fillMaxWidth()) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .heightIn(min = maxHeight),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = annotatedStringResource(R.string.cocktail_six_message_3),
                    style = DefaultTextStyles.body,
                    textAlign = TextAlign.Center,
                    modifier =
                        Modifier
                            .widthIn(max = CONTENT_MAX_WIDTH)
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                )
                Image(
                    painter = painterResource(R.drawable.image_parchemin_nappe),
                    contentDescription = stringResource(R.string.salle_cocktails_enigme_accessibility),
                    contentScale = ContentScale.FillWidth,
                    modifier =
                        Modifier
                            .widthIn(max = IMAGE_MAX_WIDTH)
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                )
                CustomInput(
                    state = answerState,
                    hint = stringResource(R.string.reponse),
                    modifier =
                        Modifier
                            .widthIn(max = CONTENT_MAX_WIDTH)
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                )
            }
        }

        AppButton(
            text = stringResource(R.string.valider),
            onClick = { onValidateClick(answer) },
            enabled = answer.isNotEmpty(),
            modifier =
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .widthIn(max = CONTENT_MAX_WIDTH)
                    .fillMaxWidth(0.7f)
                    .padding(vertical = 16.dp),
        )
    }
}

@Preview(showSystemUi = true, name = "Cocktails 6")
@Composable
private fun CocktailsSixScreenPreview() {
    CocktailsSixScreen(
        playerName = "Pauline",
        onHomeClick = {},
        onValidateClick = {},
    )
}

@Preview(showSystemUi = true, name = "Cocktails 6 - énigme")
@Composable
private fun RiddlePagePreview() {
    AppTheme {
        RiddlePage(
            answerState = rememberTextFieldState(),
            onValidateClick = {},
        )
    }
}

@Preview(showSystemUi = true, name = "Cocktails 6 - paysage", widthDp = 800, heightDp = 360)
@Composable
private fun CocktailsSixScreenLandscapePreview() {
    CocktailsSixScreen(
        playerName = "Pauline",
        onHomeClick = {},
        onValidateClick = {},
    )
}
