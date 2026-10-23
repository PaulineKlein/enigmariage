package com.pklein.mariage.ui.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.pklein.mariage.R
import com.pklein.mariage.ui.atome.AppButton
import com.pklein.mariage.ui.atome.ClueButton
import com.pklein.mariage.ui.atome.CustomInput
import com.pklein.mariage.ui.atome.ZoomHint
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.DefaultTextStyles
import com.pklein.mariage.utils.extension.showImageWithStfalconViewer

@Composable
fun QuestionScreen(
    @StringRes title: Int,
    message: AnnotatedString,
    onHomeClick: () -> Unit,
    onValidateClick: (String) -> Unit,
    enigmeImage: Int? = null,
    enigmeImageDescription: String? = null,
    enigmeImageZoomable: Boolean = false,
    enigmeLottie: String? = null,
    enigmeLottieInfinite: Boolean = false,
    enigmeContent: (@Composable () -> Unit)? = null,
    onClueClick: (() -> Unit)? = null,
) {
    var answer by remember { mutableStateOf("") }
    val context = LocalContext.current

    AppTheme {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .systemBarsPadding()
                    .imePadding()
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

                if (enigmeLottie != null) {
                    Spacer(modifier = Modifier.height(24.dp))
                    val composition by rememberLottieComposition(
                        LottieCompositionSpec.Asset(
                            enigmeLottie,
                        ),
                    )
                    LottieAnimation(
                        composition = composition,
                        iterations = if (enigmeLottieInfinite) LottieConstants.IterateForever else 1,
                        modifier =
                            Modifier
                                .size(120.dp)
                                .align(Alignment.CenterHorizontally),
                    )
                }

                if (enigmeImage != null) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Image(
                        painter = painterResource(enigmeImage),
                        contentDescription = enigmeImageDescription,
                        contentScale = ContentScale.FillWidth,
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .then(
                                    if (enigmeImageZoomable) {
                                        Modifier.clickable { showImageWithStfalconViewer(context, enigmeImage) }
                                    } else {
                                        Modifier
                                    },
                                ),
                    )
                    if (enigmeImageZoomable) {
                        ZoomHint(
                            modifier =
                                Modifier
                                    .padding(top = 8.dp)
                                    .align(Alignment.CenterHorizontally),
                        )
                    }
                }

                if (enigmeContent != null) {
                    Spacer(modifier = Modifier.height(24.dp))
                    enigmeContent()
                }

                Spacer(modifier = Modifier.height(24.dp))

                CustomInput(
                    value = answer,
                    onValueChange = { answer = it },
                    hint = stringResource(id = R.string.reponse),
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (onClueClick != null) {
                    AppButton(
                        text = stringResource(id = R.string.valider),
                        onClick = { onValidateClick(answer) },
                        enabled = answer.isNotBlank(),
                        modifier = Modifier.fillMaxWidth(0.5f),
                    )
                    ClueButton(
                        text = stringResource(id = R.string.indice),
                        onClick = onClueClick,
                    )
                } else {
                    AppButton(
                        text = stringResource(id = R.string.valider),
                        onClick = { onValidateClick(answer) },
                        enabled = answer.isNotBlank(),
                        modifier = Modifier.fillMaxWidth(0.7f),
                    )
                }
            }
        }
    }
}

@Preview(name = "Sans image ni indice")
@Composable
private fun QuestionScreenSimplePreview() {
    QuestionScreen(
        title = R.string.reception_titre,
        message = AnnotatedString("Trouve la couleur du bouquet de la mariée."),
        onHomeClick = {},
        onValidateClick = {},
    )
}

@Preview(name = "Avec animation Lottie")
@Composable
private fun QuestionScreenWithLottiePreview() {
    QuestionScreen(
        title = R.string.reception_titre,
        message = AnnotatedString("Trouve la couleur du bouquet de la mariée."),
        enigmeLottie = "animation_question_mark.json",
        onHomeClick = {},
        onValidateClick = {},
    )
}

@Preview(name = "Avec image et indice")
@Composable
private fun QuestionScreenWithImageAndCluePreview() {
    QuestionScreen(
        title = R.string.reception_titre,
        message = AnnotatedString("Cherchons un indice dans cette image !\nCompte le nombre de bateaux, de lunettes et d'objets roses."),
        enigmeImage = R.drawable.image_parchemin_sirene,
        enigmeImageDescription = "Parchemin de la sirène",
        onHomeClick = {},
        onValidateClick = {},
        onClueClick = {},
    )
}

@Preview(showBackground = true, name = "Message long")
@Composable
private fun QuestionScreenLongMessagePreview() {
    QuestionScreen(
        title = R.string.reception_titre,
        message =
            AnnotatedString(
                "Essaierait-il de cacher son élixir sous forme de cocktail ? " +
                    "A quoi peuvent bien servir ces chiffres 1, 2 et 4 : garde-les bien en tête.\n\n" +
                    "D'ailleurs de quelle couleur était le cocktail de tout à l'heure ? " +
                    "C'est peut-être un autre indice…",
            ),
        onHomeClick = {},
        onValidateClick = {},
        onClueClick = {},
    )
}
