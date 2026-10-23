package com.pklein.mariage.presentation.destinations.tanzanie.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pklein.mariage.R
import com.pklein.mariage.ui.atome.AppButton
import com.pklein.mariage.ui.atome.OldPhone
import com.pklein.mariage.ui.atome.ParcheminCard
import com.pklein.mariage.ui.screen.MyNavigationBar
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.DefaultTextStyles
import com.pklein.mariage.ui.theme.annotatedStringResource

@Composable
fun TanzanieSevenScreen(
    onHomeClick: () -> Unit,
    onValidateClick: (String) -> Unit,
) {
    var code by rememberSaveable { mutableStateOf("") }

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
                text = annotatedStringResource(id = R.string.diner_tanzanie_seven_message),
                style = DefaultTextStyles.body,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(24.dp))

            ParcheminCard(
                text = stringResource(id = R.string.diner_tanzanie_seven_code),
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(24.dp))

            OldPhone(
                value = code,
                onValueChange = { code = it },
            )
        }
        AppButton(
            text = stringResource(id = R.string.valider),
            onClick = { onValidateClick(code) },
            enabled = code.isNotBlank(),
            modifier =
                Modifier
                    .padding(top = 24.dp)
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.7f),
        )
    }
}

@Preview(showBackground = true, name = "Tanzanie 7")
@Composable
private fun TanzanieSevenScreenPreview() {
    AppTheme {
        TanzanieSevenScreen(
            onHomeClick = {},
            onValidateClick = {},
        )
    }
}
