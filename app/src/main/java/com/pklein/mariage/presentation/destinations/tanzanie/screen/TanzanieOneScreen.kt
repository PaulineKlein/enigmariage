package com.pklein.mariage.presentation.destinations.tanzanie.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pklein.mariage.R
import com.pklein.mariage.ui.atome.AppButton
import com.pklein.mariage.ui.atome.ParcheminCard
import com.pklein.mariage.ui.screen.MyNavigationBar
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.DefaultTextStyles
import com.pklein.mariage.ui.theme.annotatedStringResource

private val choices =
    listOf(
        R.string.diner_tanzanie_one_choice_1,
        R.string.diner_tanzanie_one_choice_2,
        R.string.diner_tanzanie_one_choice_3,
    )

@Composable
fun TanzanieOneScreen(
    onHomeClick: () -> Unit,
    onValidateClick: (Int) -> Unit,
) {
    var selected by rememberSaveable { mutableStateOf<Int?>(null) }

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
                title = R.string.diner_titre_tanzanie,
                onHomeClick = onHomeClick,
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = annotatedStringResource(id = R.string.diner_tanzanie_one_message),
                style = DefaultTextStyles.body,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(24.dp))

            Image(
                painter = painterResource(R.drawable.image_tanzanie),
                contentDescription = stringResource(id = R.string.image_enigme_accessibility),
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(24.dp))

            ParcheminCard(text = stringResource(id = R.string.diner_tanzanie_one_indices))

            Spacer(modifier = Modifier.height(24.dp))

            choices.forEachIndexed { index, choice ->
                val number = index + 1
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = selected == number,
                                onClick = { selected = number },
                                role = Role.RadioButton,
                            )
                            .padding(vertical = 8.dp),
                ) {
                    RadioButton(
                        selected = selected == number,
                        onClick = null,
                    )
                    Text(
                        text = stringResource(id = choice),
                        style = DefaultTextStyles.body,
                        modifier =
                            Modifier
                                .weight(1f)
                                .padding(start = 12.dp),
                    )
                }
            }
        }

        AppButton(
            text = stringResource(id = R.string.valider),
            onClick = { selected?.let(onValidateClick) },
            enabled = selected != null,
            modifier =
                Modifier
                    .padding(top = 24.dp)
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.7f),
        )
    }
}

@Preview(showBackground = true, name = "Tanzanie 1")
@Composable
private fun TanzanieOneScreenPreview() {
    AppTheme {
        TanzanieOneScreen(
            onHomeClick = {},
            onValidateClick = {},
        )
    }
}
