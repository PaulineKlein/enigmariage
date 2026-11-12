package com.pklein.mariage.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    val colorScheme =
        lightColorScheme(
            primary = ColorPrimary,
            onPrimary = Color.White,
            secondary = ColorAccent,
            onSecondary = Color.White,
            background = ColorCadenaBackground,
            onBackground = ColorText,
            surface = ColorCadenaBackground,
            onSurface = ColorText,
            error = ColorErreur,
        )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = defaultTypography,
        shapes = Shapes(),
    ) {
        Box(modifier = Modifier.fillMaxSize().background(WindowBackgroundBrush)) {
            content()
        }
    }
}
