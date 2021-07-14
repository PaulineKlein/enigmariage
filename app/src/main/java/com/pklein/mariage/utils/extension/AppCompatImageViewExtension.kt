package com.pklein.mariage.utils.extension

import android.content.Context
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat

fun AppCompatImageView.defineButton(
    drawable: Int,
    isEnabled: Boolean,
    context: Context
) {
    this.background = ContextCompat.getDrawable(context, drawable)
    this.isEnabled = isEnabled
    this.isFocusable = isEnabled
}