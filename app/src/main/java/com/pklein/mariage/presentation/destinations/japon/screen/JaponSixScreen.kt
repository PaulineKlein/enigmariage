package com.pklein.mariage.presentation.destinations.japon.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pklein.mariage.R
import com.pklein.mariage.ui.atome.AppButton
import com.pklein.mariage.ui.atome.CustomInput
import com.pklein.mariage.ui.atome.PagerDots
import com.pklein.mariage.ui.atome.ParcheminImageCard
import com.pklein.mariage.ui.atome.RoundedImage
import com.pklein.mariage.ui.modifier.zoomOutTransform
import com.pklein.mariage.ui.screen.MyNavigationBar
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.DefaultTextStyles

private const val PAGE_COUNT = 2

@Composable
fun JaponSixScreen(
    onHomeClick: () -> Unit,
    onValidateClick: (String) -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { PAGE_COUNT })
    var answer by remember { mutableStateOf("") }

    AppTheme {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .safeDrawingPadding()
                    .imePadding()
                    .padding(16.dp),
        ) {
            MyNavigationBar(
                title = R.string.diner_titre_japon,
                onHomeClick = onHomeClick,
            )

            HorizontalPager(
                state = pagerState,
                modifier =
                    Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(top = 4.dp),
            ) { page ->
                Column(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .zoomOutTransform(pagerState, page),
                ) {
                    if (page == 0) {
                        TapiocaPage()
                    } else {
                        TicketPage(
                            answer = answer,
                            onAnswerChange = { answer = it },
                            onValidateClick = onValidateClick,
                        )
                    }
                }
            }

            PagerDots(pageCount = PAGE_COUNT, currentPage = pagerState.currentPage)
        }
    }
}

@Composable
private fun TapiocaPage() {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
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
                text = stringResource(id = R.string.diner_japon_six_message_1),
                style = DefaultTextStyles.bodyBig,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            RoundedImage(
                image = R.drawable.image_tapioca,
                contentDescription = null,
                modifier = Modifier.padding(top = 16.dp),
            )
        }
    }
}

@Composable
private fun TicketPage(
    answer: String,
    onAnswerChange: (String) -> Unit,
    onValidateClick: (String) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier =
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(id = R.string.diner_japon_six_message_2),
                style = DefaultTextStyles.body,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(24.dp))
            ParcheminImageCard(
                image = R.drawable.image_code_templier_enigme_cambodge,
                contentDescription = stringResource(id = R.string.image_enigme_accessibility),
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(id = R.string.diner_japon_six_message_3),
                style = DefaultTextStyles.body,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(24.dp))
            RoundedImage(
                image = R.drawable.image_code_templier,
                contentDescription = stringResource(id = R.string.image_indice_accessibility),
            )
            Spacer(modifier = Modifier.height(24.dp))
            CustomInput(
                value = answer,
                onValueChange = onAnswerChange,
                hint = stringResource(id = R.string.embarquement_three_reponse_hint),
                modifier = Modifier.fillMaxWidth(),
            )
        }

        AppButton(
            text = stringResource(id = R.string.valider),
            onClick = { onValidateClick(answer) },
            enabled = answer.isNotBlank(),
            modifier =
                Modifier
                    .fillMaxWidth(0.7f)
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 8.dp, bottom = 16.dp),
        )
    }
}

@Preview(showSystemUi = true, name = "Japon 6")
@Composable
private fun JaponSixScreenPreview() {
    JaponSixScreen(
        onHomeClick = {},
        onValidateClick = {},
    )
}

@Preview(showSystemUi = true, name = "Japon 6 - Ticket")
@Composable
private fun TicketPagePreview() {
    AppTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            TicketPage(
                answer = "",
                onAnswerChange = {},
                onValidateClick = {},
            )
        }
    }
}

@Preview(showSystemUi = true, name = "Japon 6 - Tapioca")
@Composable
private fun TapiocaPagePreview() {
    AppTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            TapiocaPage()
        }
    }
}
