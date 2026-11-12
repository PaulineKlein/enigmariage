package com.pklein.mariage.presentation.destinations.japon.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.sp
import com.pklein.mariage.R
import com.pklein.mariage.ui.atome.ParcheminCard
import com.pklein.mariage.ui.screen.QuestionScreen
import com.pklein.mariage.ui.theme.DefaultTextStyles
import com.pklein.mariage.ui.theme.annotatedStringResource
import com.pklein.mariage.ui.theme.splashFontFamily

private val MORSE_FONT_SIZE = 36.sp

@Composable
fun JaponFourScreen(
    onHomeClick: () -> Unit,
    onValidateClick: (String) -> Unit,
) {
    QuestionScreen(
        title = R.string.diner_titre_japon,
        message = annotatedStringResource(id = R.string.diner_japon_four_message_1),
        hint = R.string.embarquement_three_reponse_hint,
        enigmeContent = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                ParcheminCard(
                    text = stringResource(id = R.string.diner_japon_four_enigme),
                    textAlign = TextAlign.Center,
                    textStyle =
                        DefaultTextStyles.bodyParchemin.copy(
                            fontFamily = splashFontFamily,
                            fontSize = MORSE_FONT_SIZE,
                            lineHeight = MORSE_FONT_SIZE * 1.2f,
                        ),
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = stringResource(id = R.string.diner_japon_four_message_2),
                    style = DefaultTextStyles.body,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(24.dp))
                Image(
                    painter = painterResource(R.drawable.image_alphabet_morse),
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxWidth(),
                    contentDescription = stringResource(id = R.string.image_indice_accessibility),
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = stringResource(id = R.string.embarquement_three_question),
                    style = DefaultTextStyles.body.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center,
                )
            }
        },
        onHomeClick = onHomeClick,
        onValidateClick = onValidateClick,
    )
}

@Preview(showSystemUi = true, name = "Japon 4")
@Composable
private fun JaponFourScreenPreview() {
    JaponFourScreen(
        onHomeClick = {},
        onValidateClick = {},
    )
}
