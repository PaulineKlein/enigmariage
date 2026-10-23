package com.pklein.mariage.ui.atome

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.ColorGrey
import com.pklein.mariage.ui.theme.ColorText
import com.pklein.mariage.ui.theme.DefaultTextStyles
import com.pklein.mariage.ui.theme.GradientEndColor
import com.pklein.mariage.ui.theme.GradientEndColorFonce

/** Compose equivalent of style="@style/button". */
@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.heightIn(min = 48.dp),
        shape = RoundedCornerShape(12.dp),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = GradientEndColor,
                contentColor = ColorText,
                disabledContainerColor = ColorGrey,
                disabledContentColor = ColorText,
            ),
        border = BorderStroke(1.dp, if (enabled) GradientEndColorFonce else ColorGrey),
        elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp),
        contentPadding = PaddingValues(13.dp),
    ) {
        Text(
            text = text.uppercase(Locale.current.platformLocale),
            style = DefaultTextStyles.body.copy(fontSize = 14.sp),
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(name = "Activé")
@Composable
private fun AppButtonEnabledPreview() {
    AppTheme {
        AppButton(
            text = "Valider",
            onClick = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(name = "Désactivé")
@Composable
private fun AppButtonDisabledPreview() {
    AppTheme {
        AppButton(
            text = "Valider",
            onClick = {},
            enabled = false,
            modifier = Modifier.padding(16.dp),
        )
    }
}
