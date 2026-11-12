package com.pklein.mariage.presentation.destinations.canada.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pklein.mariage.R
import com.pklein.mariage.ui.atome.ParcheminImageCard
import com.pklein.mariage.ui.atome.RoundedImage
import com.pklein.mariage.ui.screen.QuestionScreen
import com.pklein.mariage.ui.theme.DefaultTextStyles
import com.pklein.mariage.ui.theme.annotatedStringResource

@Composable
fun CanadaSixScreen(
    onHomeClick: () -> Unit,
    onValidateClick: (String) -> Unit,
) {
    QuestionScreen(
        title = R.string.diner_titre_canada,
        message = annotatedStringResource(id = R.string.diner_canada_six_message_1),
        hint = R.string.embarquement_three_reponse_hint,
        enigmeContent = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                ParcheminImageCard(
                    image = R.drawable.image_code_templier_enigme_japon,
                    contentDescription = stringResource(id = R.string.image_enigme_accessibility),
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = stringResource(id = R.string.diner_canada_six_message_2),
                    style = DefaultTextStyles.body,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(24.dp))
                RoundedImage(
                    image = R.drawable.image_code_templier,
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

@Preview(showSystemUi = true, name = "Canada 6")
@Composable
private fun CanadaSixScreenPreview() {
    CanadaSixScreen(
        onHomeClick = {},
        onValidateClick = {},
    )
}
