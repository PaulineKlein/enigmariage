package com.pklein.mariage.ui.theme

import android.text.Spanned
import android.text.style.StyleSpan
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

/** Like stringResource, but keeps <b> and <i> styling instead of stripping the tags. */
@Composable
fun annotatedStringResource(
    @StringRes id: Int,
): AnnotatedString =
    LocalResources
        .current
        .getText(id)
        .toAnnotatedString()

private fun CharSequence.toAnnotatedString(): AnnotatedString {
    val text = this
    return buildAnnotatedString {
        append(text.toString())
        if (text is Spanned) {
            text.getSpans(0, text.length, StyleSpan::class.java).forEach { span ->
                val style =
                    when (span.style) {
                        android.graphics.Typeface.BOLD -> {
                            SpanStyle(fontWeight = FontWeight.Bold)
                        }

                        android.graphics.Typeface.ITALIC -> {
                            SpanStyle(fontStyle = FontStyle.Italic)
                        }

                        android.graphics.Typeface.BOLD_ITALIC -> {
                            SpanStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)
                        }

                        else -> {
                            null
                        }
                    }
                if (style != null) addStyle(style, text.getSpanStart(span), text.getSpanEnd(span))
            }
        }
    }
}
