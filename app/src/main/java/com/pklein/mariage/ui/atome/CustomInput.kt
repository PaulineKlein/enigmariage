package com.pklein.mariage.ui.atome

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pklein.mariage.R
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.ColorHint
import com.pklein.mariage.ui.theme.ColorPrimary
import com.pklein.mariage.ui.theme.DefaultTextStyles

/** Compose equivalent of style="@style/CustomInput". */
@Composable
fun CustomInput(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val textStyle = DefaultTextStyles.bodyBig

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        singleLine = true,
        textStyle = textStyle,
        cursorBrush = SolidColor(ColorPrimary),
        interactionSource = interactionSource,
        keyboardOptions =
            KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
            ),
        keyboardActions = keyboardActions,
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier =
                    Modifier
                        .border(1.dp, ColorPrimary, RoundedCornerShape(12.dp))
                        .padding(13.dp),
            ) {
                Image(
                    painter = painterResource(if (isFocused) R.drawable.ic_arrow_focused else R.drawable.ic_arrow),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(13.dp))
                Box(modifier = Modifier.weight(1f)) {
                    if (value.isEmpty()) {
                        Text(text = hint, style = textStyle.copy(color = ColorHint))
                    }
                    innerTextField()
                }
            }
        },
    )
}

@Preview(name = "Vide (hint)")
@Composable
private fun CustomInputEmptyPreview() {
    AppTheme {
        CustomInput(
            value = "",
            onValueChange = {},
            hint = "Réponse",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        )
    }
}

@Preview(name = "Avec texte")
@Composable
private fun CustomInputFilledPreview() {
    AppTheme {
        CustomInput(
            value = "Ma réponse",
            onValueChange = {},
            hint = "Réponse",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        )
    }
}

@Preview(name = "Interactif")
@Composable
private fun CustomInputInteractivePreview() {
    var value by remember { mutableStateOf("") }
    AppTheme {
        CustomInput(
            value = value,
            onValueChange = { value = it },
            hint = "Réponse",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        )
    }
}
