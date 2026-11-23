package com.pklein.mariage.ui.atome

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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

@Composable
fun CustomInput(
    state: TextFieldState,
    hint: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    inputTransformation: InputTransformation? = null,
    onKeyboardAction: KeyboardActionHandler? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val textStyle = DefaultTextStyles.bodyBig

    BasicTextField(
        state = state,
        modifier = modifier,
        enabled = enabled,
        lineLimits = TextFieldLineLimits.SingleLine,
        textStyle = textStyle,
        cursorBrush = SolidColor(ColorPrimary),
        interactionSource = interactionSource,
        inputTransformation = inputTransformation,
        keyboardOptions =
            KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = ImeAction.Done,
            ),
        onKeyboardAction = onKeyboardAction,
        decorator = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier =
                    Modifier
                        .border(1.dp, ColorPrimary, RoundedCornerShape(12.dp))
                        .padding(13.dp),
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_next),
                    contentDescription = null,
                    tint = if (isFocused) ColorPrimary else ColorHint,
                )
                Spacer(modifier = Modifier.width(13.dp))
                Box(modifier = Modifier.weight(1f)) {
                    if (state.text.isEmpty()) {
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
            state = rememberTextFieldState(),
            hint = "Réponse",
            modifier =
                Modifier
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
            state = rememberTextFieldState("Ma réponse"),
            hint = "Réponse",
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
        )
    }
}

@Preview(name = "Interactif")
@Composable
private fun CustomInputInteractivePreview() {
    AppTheme {
        CustomInput(
            state = rememberTextFieldState(),
            hint = "Réponse",
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
        )
    }
}
