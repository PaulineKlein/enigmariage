package com.pklein.mariage.ui.atome

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pklein.mariage.R
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.ColorParchemin
import com.pklein.mariage.ui.theme.ColorParcheminText

private val IMAGE_CORNER_RADIUS = 8.dp

@Composable
fun ParcheminImageCard(
    @DrawableRes image: Int,
    contentDescription: String?,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = ColorParchemin),
        border = BorderStroke(2.dp, ColorParcheminText),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        RoundedImage(
            image = image,
            contentDescription = contentDescription,
            cornerRadius = IMAGE_CORNER_RADIUS,
            elevated = false,
            modifier = Modifier.padding(12.dp),
        )
    }
}

@Preview(showBackground = true, name = "Image sur parchemin")
@Composable
private fun ParcheminImageCardPreview() {
    AppTheme {
        ParcheminImageCard(
            image = R.drawable.image_code_templier_enigme_japon,
            contentDescription = null,
            modifier = Modifier.padding(16.dp),
        )
    }
}
