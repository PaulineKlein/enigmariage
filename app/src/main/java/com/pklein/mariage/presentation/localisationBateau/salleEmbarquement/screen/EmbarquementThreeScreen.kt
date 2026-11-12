package com.pklein.mariage.presentation.localisationBateau.salleEmbarquement.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pklein.mariage.R
import com.pklein.mariage.ui.atome.HelpCard
import com.pklein.mariage.ui.atome.ParcheminCard
import com.pklein.mariage.ui.screen.QuestionScreen
import com.pklein.mariage.ui.theme.DefaultTextStyles
import com.pklein.mariage.ui.theme.annotatedStringResource
import com.pklein.mariage.ui.theme.splashFontFamily

private val CODE_FONT_SIZE = 32.sp
private val CODE_LETTER_SPACING = 2.sp

/** [enigme] and [description] depend on the chosen inspector. */
@Composable
fun EmbarquementThreeScreen(
    @StringRes enigme: Int,
    @StringRes description: Int,
    onHomeClick: () -> Unit,
    onClueClick: () -> Unit,
    onValidateClick: (String) -> Unit,
) {
    QuestionScreen(
        title = R.string.embarquement_one_titre,
        message = annotatedStringResource(id = R.string.embarquement_three_message_1),
        hint = R.string.embarquement_three_reponse_hint,
        enigmeContent = {
            Column {
                ParcheminCard(
                    text = stringResource(id = enigme),
                    textAlign = TextAlign.Center,
                    textStyle =
                        DefaultTextStyles.bodyParchemin.copy(
                            fontFamily = splashFontFamily,
                            fontSize = CODE_FONT_SIZE,
                            lineHeight = CODE_FONT_SIZE * 1.2f,
                            letterSpacing = CODE_LETTER_SPACING,
                        ),
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = stringResource(id = R.string.embarquement_three_message_2),
                    style = DefaultTextStyles.body,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(16.dp))
                HelpCard(text = annotatedStringResource(id = description))
                Spacer(modifier = Modifier.height(16.dp))
            }
        },
        onHomeClick = onHomeClick,
        onClueClick = onClueClick,
        onValidateClick = onValidateClick,
    )
}

@Preview(showSystemUi = true, name = "Embarquement 3")
@Composable
private fun EmbarquementThreeScreenPreview() {
    EmbarquementThreeScreen(
        enigme = R.string.embarquement_three_enigme_rabbit,
        description = R.string.embarquement_three_help_rabbit,
        onHomeClick = {},
        onClueClick = {},
        onValidateClick = {},
    )
}
