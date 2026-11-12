package com.pklein.mariage.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.pklein.mariage.R

val defaultFontFamily =
    FontFamily(
        Font(R.font.balsamiq_sans_regular, FontWeight.Normal),
        Font(R.font.balsamiq_sans_italic, FontWeight.Normal, FontStyle.Italic),
        Font(R.font.balsamiq_sans_bold_italic, FontWeight.Bold, FontStyle.Italic),
        Font(R.font.balsamiq_sans_bold, FontWeight.Bold),
    )
val splashFontFamily = FontFamily(Font(R.font.batikworldwide))

val titleFontFamily = FontFamily(Font(R.font.quark_cheese))

object DefaultTextStyles {
    val titreSplash: TextStyle
        get() =
            TextStyle(
                fontFamily = splashFontFamily,
                fontSize = 48.sp,
                color = ColorBleuFonce,
                textAlign = TextAlign.Center,
            )
    val titre: TextStyle
        get() =
            TextStyle(
                fontFamily = titleFontFamily,
                fontSize = 40.sp,
                color = ColorBleuFonce,
                textAlign = TextAlign.Center,
            )

    val sousTitre: TextStyle
        get() =
            TextStyle(
                fontFamily = defaultFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = ColorPrimaryDark,
            )

    val body: TextStyle
        get() =
            TextStyle(
                fontFamily = defaultFontFamily,
                fontSize = 16.sp,
                color = ColorText,
                lineHeight = 16.sp * 1.2f,
            )

    val bodyLink: TextStyle
        get() =
            TextStyle(
                fontFamily = defaultFontFamily,
                fontSize = 18.sp,
                color = ColorBleuFonce,
                lineHeight = 18.sp * 1.2f,
                textAlign = TextAlign.Center,
            )

    val bodyBig: TextStyle
        get() =
            TextStyle(
                fontFamily = defaultFontFamily,
                fontSize = 18.sp,
                color = ColorText,
                lineHeight = 18.sp * 1.2f,
            )

    val bodyParchemin: TextStyle
        get() =
            TextStyle(
                fontFamily = defaultFontFamily,
                fontSize = 16.sp,
                color = ColorParcheminText,
                lineHeight = 16.sp * 1.2f,
                textAlign = TextAlign.Center,
            )

    val success: TextStyle
        get() =
            TextStyle(
                fontFamily = defaultFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = ColorBleuFonce,
                lineHeight = 18.sp * 1.2f,
            )

    val error: TextStyle
        get() =
            TextStyle(
                fontFamily = defaultFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = ColorErreur,
                lineHeight = 18.sp * 1.2f,
            )
}

val defaultTypography =
    Typography(
        displayLarge = DefaultTextStyles.titreSplash,
        displayMedium = DefaultTextStyles.titre,
        displaySmall = DefaultTextStyles.sousTitre,
        headlineLarge = DefaultTextStyles.sousTitre,
        headlineMedium = DefaultTextStyles.sousTitre,
        headlineSmall = DefaultTextStyles.sousTitre,
        titleLarge = DefaultTextStyles.titreSplash,
        titleMedium = DefaultTextStyles.titre,
        titleSmall = DefaultTextStyles.sousTitre,
        bodyLarge = DefaultTextStyles.bodyBig,
        bodyMedium = DefaultTextStyles.body,
        bodySmall = DefaultTextStyles.body,
        labelLarge = DefaultTextStyles.bodyParchemin,
        labelMedium = DefaultTextStyles.bodyParchemin,
        labelSmall = DefaultTextStyles.bodyParchemin,
    )
