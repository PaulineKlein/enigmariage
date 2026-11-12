package com.pklein.mariage.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pklein.mariage.ui.atome.CadenaKeypad
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.DefaultTextStyles

/** A question with a digit keypad, like fragment_salle_cocktails_one_2.xml. */
@Composable
fun CadenaPage(
    question: String,
    onValidateClick: (String) -> Unit,
) {
    var value by remember { mutableStateOf("") }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .heightIn(min = maxHeight),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = question,
                style = DefaultTextStyles.bodyBig,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(32.dp))
            CadenaKeypad(
                value = value,
                onValueChange = { value = it },
                onValidate = onValidateClick,
            )
        }
    }
}

@Preview(name = "Page cadenas", showSystemUi = true)
@Composable
private fun CadenaPagePreview() {
    AppTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            CadenaPage(
                question = "Place les chiffres obtenus dans l'ordre croissant.",
                onValidateClick = {},
            )
        }
    }
}
