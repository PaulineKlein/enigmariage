package com.pklein.mariage.ui.theme

import android.content.res.Resources
import android.text.SpannableStringBuilder
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

private val PLACEHOLDER = Regex("%(\\d+\\$)?s")

/** Like stringResource, but keeps <b> and <i> styling instead of stripping the tags. */
@Composable
fun annotatedStringResource(
    @StringRes id: Int,
    vararg formatArgs: Any?,
): AnnotatedString =
    LocalResources
        .current
        .getFormattedText(id, *formatArgs)
        .toAnnotatedString()

/** Like getString(id, args), but keeps <b> and <i> spans so it can be set on a TextView. */
fun Resources.getFormattedText(
    @StringRes id: Int,
    vararg formatArgs: Any?,
): CharSequence = getText(id).formatKeepingSpans(formatArgs)

// getString(id, args) would drop the spans, so %s / %1$s are replaced directly in the Spanned.
private fun CharSequence.formatKeepingSpans(args: Array<out Any?>): CharSequence {
    if (args.isEmpty()) return this
    var sequentialIndex = 0
    val replacements =
        PLACEHOLDER
            .findAll(this)
            .map { match ->
                val argIndex =
                    match.groups[1]
                        ?.value
                        ?.dropLast(1)
                        ?.toInt()
                        ?.minus(1) ?: sequentialIndex++
                match.range to (args[argIndex]?.toString().orEmpty())
            }.toList()
    return SpannableStringBuilder(this).apply {
        replacements
            .asReversed()
            .forEach { (range, value) -> replace(range.first, range.last + 1, value) }
    }
}

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
