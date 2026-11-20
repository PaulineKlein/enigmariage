package com.pklein.mariage.ui.modifier

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager

/** Clears focus (and so closes the keyboard) when tapping outside a text field; taps handled by children are ignored. */
@Composable
fun Modifier.clearFocusOnTap(): Modifier {
    val focusManager = LocalFocusManager.current
    return pointerInput(focusManager) {
        detectTapGestures { focusManager.clearFocus() }
    }
}
