package com.pklein.mariage.ui.atome

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pklein.mariage.R
import com.pklein.mariage.ui.theme.AppTheme

private val DEFAULT_CORNER_RADIUS = 20.dp
private val ELEVATION = 4.dp

@Composable
fun RoundedImage(
    @DrawableRes image: Int,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = DEFAULT_CORNER_RADIUS,
    elevated: Boolean = true,
) {
    val shape = RoundedCornerShape(cornerRadius)
    Image(
        painter = painterResource(image),
        contentDescription = contentDescription,
        contentScale = ContentScale.FillWidth,
        modifier =
            modifier
                .fillMaxWidth()
                .then(if (elevated) Modifier.shadow(ELEVATION, shape) else Modifier)
                .clip(shape),
    )
}

@Preview(showBackground = true, name = "Image arrondie")
@Composable
private fun RoundedImagePreview() {
    AppTheme {
        RoundedImage(
            image = R.drawable.image_tapioca,
            contentDescription = null,
            modifier = Modifier.padding(16.dp),
        )
    }
}
