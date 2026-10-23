package com.pklein.mariage.ui.atome

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.ColorHelp
import com.pklein.mariage.ui.theme.ColorHelpFonce
import com.pklein.mariage.ui.theme.ColorHelpText
import com.pklein.mariage.ui.theme.DefaultTextStyles

@Composable
fun HelpCard(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = ColorHelp),
        border = BorderStroke(2.dp, ColorHelpFonce),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Text(
            text = text,
            style = DefaultTextStyles.bodyParchemin.copy(color = ColorHelpText),
            textAlign = textAlign,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 24.dp),
        )
    }
}

@Preview(showBackground = true, name = "Aide")
@Composable
private fun HelpCardPreview() {
    AppTheme {
        HelpCard(
            text = AnnotatedString("Même avec l’aide d’un Avocat vous ne m’arrêterez pas."),
            modifier = Modifier.padding(16.dp),
        )
    }
}
