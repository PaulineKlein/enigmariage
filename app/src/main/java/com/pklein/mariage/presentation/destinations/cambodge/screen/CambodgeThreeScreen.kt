package com.pklein.mariage.presentation.destinations.cambodge.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pklein.mariage.R
import com.pklein.mariage.ui.atome.AppButton
import com.pklein.mariage.ui.atome.RadioCard
import com.pklein.mariage.ui.atome.RadioCardGroup
import com.pklein.mariage.ui.screen.MyNavigationBar
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.DefaultTextStyles
import com.pklein.mariage.ui.theme.annotatedStringResource

private val CONTENT_MAX_WIDTH = 480.dp
private val KANJI_MAX_WIDTH = 300.dp
private val CARD_CORNER_RADIUS = 16.dp
private const val NO_ANSWER = 0

private val answers =
    listOf(
        R.string.diner_cambodge_three_reponse_2,
        R.string.diner_cambodge_three_reponse_3,
        R.string.diner_cambodge_three_reponse_1,
        R.string.diner_cambodge_three_reponse_4,
        R.string.diner_cambodge_three_reponse_5,
    )

/** Which kata is written in Japanese and Khmer? Pick one of the five names, then validate. */
@Composable
fun CambodgeThreeScreen(
    onHomeClick: () -> Unit,
    onValidateClick: (answer: Int) -> Unit,
) {
    var selected by rememberSaveable { mutableIntStateOf(NO_ANSWER) }

    AppTheme {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .safeDrawingPadding()
                    .padding(16.dp),
        ) {
            Column(
                modifier =
                    Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                MyNavigationBar(
                    title = R.string.diner_titre_cambodge,
                    onHomeClick = onHomeClick,
                )

                Column(
                    modifier = Modifier.widthIn(max = CONTENT_MAX_WIDTH),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = annotatedStringResource(R.string.diner_cambodge_three_message_1),
                        style = DefaultTextStyles.body,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = annotatedStringResource(R.string.diner_cambodge_three_message_2),
                        style = DefaultTextStyles.body,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Image(
                        painter = painterResource(R.drawable.image_kankucho),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier =
                            Modifier
                                .widthIn(max = KANJI_MAX_WIDTH)
                                .fillMaxWidth()
                                .shadow(6.dp, RoundedCornerShape(CARD_CORNER_RADIUS))
                                .clip(RoundedCornerShape(CARD_CORNER_RADIUS))
                                .border(3.dp, Color.White, RoundedCornerShape(CARD_CORNER_RADIUS)),
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    RadioCardGroup {
                        answers.forEach { answer ->
                            RadioCard(
                                text = annotatedStringResource(answer),
                                isSelected = selected == answer,
                                onClick = { selected = answer },
                            )
                        }
                    }
                }
            }

            AppButton(
                text = stringResource(id = R.string.valider),
                onClick = { onValidateClick(selected) },
                enabled = selected != NO_ANSWER,
                modifier =
                    Modifier
                        .padding(top = 8.dp)
                        .align(Alignment.CenterHorizontally)
                        .widthIn(max = CONTENT_MAX_WIDTH)
                        .fillMaxWidth(0.7f),
            )
        }
    }
}

@Preview(showSystemUi = true, name = "Cambodge 3")
@Composable
private fun CambodgeThreeScreenPreview() {
    CambodgeThreeScreen(
        onHomeClick = {},
        onValidateClick = {},
    )
}
