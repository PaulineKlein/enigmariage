package com.pklein.mariage.ui.atome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pklein.mariage.R
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.ColorCadenaBackground
import com.pklein.mariage.ui.theme.ColorGrey
import com.pklein.mariage.ui.theme.ColorHint
import com.pklein.mariage.ui.theme.ColorPrimaryDark
import com.pklein.mariage.ui.theme.ColorText
import com.pklein.mariage.ui.theme.DefaultTextStyles

private val DigitRows =
    listOf(
        listOf(R.string.un, R.string.deux, R.string.trois),
        listOf(R.string.quatre, R.string.cinq, R.string.six),
        listOf(R.string.sept, R.string.huit, R.string.neuf),
    )

/** Compose equivalent of CadenaLayout (layout/cadena.xml). */
@Composable
fun CadenaKeypad(
    value: String,
    onValueChange: (String) -> Unit,
    onValidate: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .background(ColorPrimaryDark),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CadenaDisplay(
            value = value,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
        )

        DigitRows.forEach { row ->
            Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                row.forEachIndexed { index, digitRes ->
                    if (index > 0) Spacer(modifier = Modifier.width(16.dp))
                    val digit = stringResource(digitRes)
                    CadenaDigitButton(digit = digit, onClick = { onValueChange(value + digit) })
                }
            }
        }

        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(R.drawable.image_back),
                contentDescription = stringResource(R.string.cadena_delete_accessibility),
                modifier =
                    Modifier
                        .defaultMinSize(minWidth = 88.dp, minHeight = 48.dp)
                        .clickable { onValueChange(value.dropLast(1)) },
            )
            Spacer(modifier = Modifier.width(16.dp))
            CadenaDigitButton(
                digit = stringResource(R.string.zero),
                onClick = { onValueChange(value + "0") },
            )
            Spacer(modifier = Modifier.width(16.dp))
            val canValidate = value.isNotEmpty()
            Image(
                painter =
                    painterResource(
                        if (canValidate) R.drawable.image_check else R.drawable.image_check_disabled,
                    ),
                contentDescription = stringResource(R.string.cadena_validate_accessibility),
                modifier =
                    Modifier
                        .defaultMinSize(minWidth = 88.dp, minHeight = 48.dp)
                        .clickable(enabled = canValidate) { onValidate(value) },
            )
        }
    }
}

@Composable
private fun CadenaDisplay(
    value: String,
    modifier: Modifier = Modifier,
) {
    val textStyle = DefaultTextStyles.bodyBig.copy(fontSize = 20.sp, textAlign = TextAlign.Center)
    Box(
        contentAlignment = Alignment.Center,
        modifier =
            modifier
                .background(ColorGrey)
                .border(3.dp, ColorCadenaBackground)
                .padding(8.dp),
    ) {
        if (value.isEmpty()) {
            Text(
                text = stringResource(R.string.embarquement_one_hint),
                style = textStyle.copy(color = ColorHint),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        } else {
            Text(
                text = value,
                style = textStyle.copy(color = ColorText),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

/** Compose equivalent of style="@style/button_cadena". */
@Composable
private fun CadenaDigitButton(
    digit: String,
    onClick: () -> Unit,
) {
    val shape = RoundedCornerShape(10.dp)
    Box(
        contentAlignment = Alignment.Center,
        modifier =
            Modifier
                .defaultMinSize(minWidth = 88.dp, minHeight = 48.dp)
                .clip(shape)
                .background(ColorPrimaryDark)
                .border(3.dp, ColorCadenaBackground, shape)
                .clickable(role = Role.Button, onClick = onClick)
                .padding(horizontal = 8.dp),
    ) {
        Text(
            text = digit,
            style = DefaultTextStyles.body.copy(fontSize = 24.sp, color = ColorCadenaBackground),
        )
    }
}

@Preview(name = "Vide")
@Composable
private fun CadenaKeypadEmptyPreview() {
    AppTheme {
        CadenaKeypad(
            value = "",
            onValueChange = {},
            onValidate = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(name = "Interactif")
@Composable
private fun CadenaKeypadInteractivePreview() {
    var value by remember { mutableStateOf("124") }
    AppTheme {
        CadenaKeypad(
            value = value,
            onValueChange = { value = it },
            onValidate = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}
