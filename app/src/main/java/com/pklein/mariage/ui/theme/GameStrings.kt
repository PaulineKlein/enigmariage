package com.pklein.mariage.ui.theme

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.text.AnnotatedString
import com.pklein.mariage.data.model.GameNames

/** Implemented by the activity that knows which names the current game uses for the couple and the villain. */
interface GameNamesOwner {
    val gameNames: GameNames
}

/** The names of the game this context belongs to, or the defaults outside of a game (previews, application context). */
fun Context.gameNames(): GameNames = (findActivity() as? GameNamesOwner)?.gameNames ?: GameNames()

private tailrec fun Context.findActivity(): Activity? =
    when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> null
    }

/**
 * Story strings take the names as %1$s (bride), %2$s (groom) and %3$s (villain); [formatArgs] start at %4$s.
 * Only those placeholders are replaced, so a string with a plain "%" or no placeholder at all is safe to pass.
 */
@Composable
fun gameStringResource(
    @StringRes id: Int,
    vararg formatArgs: Any?,
): String =
    LocalResources.current
        .getFormattedText(id, *rememberGameNames().asFormatArgs(), *formatArgs)
        .toString()

/** Like [annotatedStringResource] for a story string: the names fill %1$s to %3$s and [formatArgs] start at %4$s. */
@Composable
fun gameAnnotatedStringResource(
    @StringRes id: Int,
    vararg formatArgs: Any?,
): AnnotatedString = annotatedStringResource(id, *rememberGameNames().asFormatArgs(), *formatArgs)

@Composable
private fun rememberGameNames(): GameNames {
    val context = LocalContext.current
    return remember(context) { context.gameNames() }
}

/** Like [Context.getString] for a story string: the names fill %1$s to %3$s and [formatArgs] start at %4$s. */
fun Context.getGameString(
    @StringRes id: Int,
    vararg formatArgs: Any?,
): String = getGameText(id, *formatArgs).toString()

/** Like [getGameString], but keeps the <b> and <i> spans so the text can be set on a TextView. */
fun Context.getGameText(
    @StringRes id: Int,
    vararg formatArgs: Any?,
): CharSequence = resources.getFormattedText(id, *gameNames().asFormatArgs(), *formatArgs)
