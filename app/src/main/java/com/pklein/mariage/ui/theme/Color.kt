package com.pklein.mariage.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val ColorPrimary = Color(0xFF69B4B7)
val ColorPrimaryDark = Color(0xFF3c6465)
val ColorAccent = Color(0xFFeb835e)
val ColorCadenaBackground = Color(0xFFa4c8c9)
val ColorBleuFonce = Color(0xFF002e3e)
val ColorBleuLightFonce = Color(0xFF005E85)
val ColorErreur = Color(0xFFb0171f)
val ColorGrey = Color(0xFFCCCCCC)
val ColorText = Color(0xFF454545)
val ColorHint = Color(0xFF4A7E80)
val ColorDotUnselected = Color(0xFFff879b)
val ColorParchemin = Color(0xFFe9d5b5)
val ColorParcheminText = Color(0xFFa17430)
val ColorJusCoco = Color(0xFFe8d0c0)
val ColorCassis = Color(0xFF5B2A6B)
val ColorHelp = Color(0xFFc1deb8)
val ColorHelpFonce = Color(0xFFA3C1A2)
val ColorHelpText = Color(0xFF3a6b2a)
val GradientStartColor = Color(0xFFffd6e0)
val GradientCenterColor = Color(0xFFffe8f0)
val GradientEndColor = Color(0xFFc8e6e7)
val GradientEndColorFonce = Color(0xFF8A9FA3)

// Mirrors drawable/background.xml: a 135° linear gradient (bottom-left to top-right).
val WindowBackgroundBrush = Brush.linearGradient(
    colors = listOf(GradientStartColor, GradientCenterColor, GradientEndColor),
    start = Offset(0f, Float.POSITIVE_INFINITY),
    end = Offset(Float.POSITIVE_INFINITY, 0f)
)
