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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.ColorParchemin
import com.pklein.mariage.ui.theme.ColorParcheminText
import com.pklein.mariage.ui.theme.DefaultTextStyles

@Composable
fun ParcheminCard(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = ColorParchemin),
        border = BorderStroke(2.dp, ColorParcheminText),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Text(
            text = text,
            style = DefaultTextStyles.bodyParchemin,
            textAlign = textAlign,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 24.dp),
        )
    }
}

@Preview(showBackground = true, name = "Parchemin")
@Composable
private fun ParcheminCardPreview() {
    AppTheme {
        ParcheminCard(
            text =
                "1. Casse les oeufs dans un bol, puis verse le lait de coco et le sucre.\n" +
                    "2. Rape ensuite la noix fraîche pour obtenir une texture parfaite.\n" +
                    "3. Incorpore enfin la vanille liquide à ta préparation.\n" +
                    "4. Beurre le moule et ajoute le caramel, avant de le cuire au bain-marie\n" +
                    "5. Sert le tiède.",
            modifier = Modifier.padding(16.dp),
        )
    }
}
