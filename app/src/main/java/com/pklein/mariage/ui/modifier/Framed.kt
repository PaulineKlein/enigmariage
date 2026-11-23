package com.pklein.mariage.ui.modifier

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/** Rounded picture with a soft shadow and a thin white border, like a printed photo. */
fun Modifier.framed(borderColor: Color): Modifier {
    val shape = RoundedCornerShape(16.dp)
    return this
        .shadow(6.dp, shape)
        .clip(shape)
        .border(2.dp, borderColor, shape)
}
