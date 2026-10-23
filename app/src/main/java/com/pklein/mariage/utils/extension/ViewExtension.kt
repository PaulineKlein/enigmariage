package com.pklein.mariage.utils.extension

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsCompat.Type.systemBars
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins


fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.addSystemWindowInsetToMargin() {
    val (initialLeft, initialTop, initialRight, initialBottom) =
        listOf(marginLeft, marginTop, marginRight, marginBottom)

    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        val bars = insets.getInsets(
            systemBars() or
                    WindowInsetsCompat.Type.displayCutout() or
                    WindowInsetsCompat.Type.ime()
        )

        view.updateLayoutParams {
            (this as? ViewGroup.MarginLayoutParams)?.let {
                updateMargins(
                    left = initialLeft + bars.left,
                    top = initialTop + bars.top,
                    right = initialRight + bars.right,
                    bottom = initialBottom + bars.bottom
                )
            }
        }
        insets
    }
}
