package com.pklein.mariage.ui.atome

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pklein.mariage.R
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.DefaultTextStyles
import kotlinx.coroutines.delay

private const val MULTI_TAP_DELAY_MS = 900L

private val PhoneBody = Color(0xFF2B2B2E)
private val PhoneScreen = Color(0xFFB7C99A)
private val PhoneScreenText = Color(0xFF1F2A14)
private val PhoneKey = Color(0xFF5A5C63)
private val PhoneKeyText = Color(0xFFF2F2F2)
private val PhoneKeyLetters = Color(0xFFC9CBD2)

private class PhoneKeyDef(
    val digit: Char,
    val letters: String,
)

private val LetterKeyRows =
    listOf(
        listOf(PhoneKeyDef('2', "ABC"), PhoneKeyDef('3', "DEF"), PhoneKeyDef('4', "GHI")),
        listOf(PhoneKeyDef('5', "JKL"), PhoneKeyDef('6', "MNO"), PhoneKeyDef('7', "PQRS")),
        listOf(PhoneKeyDef('8', "TUV"), PhoneKeyDef('9', "WXYZ")),
    )

/**
 * Vintage multi-tap phone: pressing a key several times in a row cycles through its letters,
 * pausing (or pressing another key) validates the current letter.
 */
@Composable
fun OldPhone(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var lastKey by remember { mutableStateOf<Char?>(null) }
    var tapCount by remember { mutableIntStateOf(0) }

    LaunchedEffect(lastKey, tapCount) {
        if (lastKey != null) {
            delay(MULTI_TAP_DELAY_MS)
            lastKey = null
        }
    }

    fun onLetterKey(key: PhoneKeyDef) {
        if (lastKey == key.digit && value.isNotEmpty()) {
            tapCount++
            val letter = key.letters[(tapCount - 1) % key.letters.length]
            onValueChange(value.dropLast(1) + letter)
        } else {
            lastKey = key.digit
            tapCount = 1
            onValueChange(value + key.letters.first())
        }
    }

    fun onSpace() {
        lastKey = null
        onValueChange(value + " ")
    }

    fun onDelete() {
        lastKey = null
        onValueChange(value.dropLast(1))
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
            modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(28.dp))
                .background(PhoneBody)
                .padding(20.dp),
    ) {
        PhoneScreen(value = value, modifier = Modifier.fillMaxWidth())

        LetterKeyRows.forEach { row ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                row.forEach { key ->
                    PhoneKeyButton(
                        label = key.digit.toString(),
                        sublabel = key.letters,
                        onClick = { onLetterKey(key) },
                    )
                }
                if (row.size < 3) {
                    PhoneKeyButton(
                        label = "0",
                        sublabel = stringResource(R.string.old_phone_space),
                        onClick = ::onSpace,
                    )
                }
            }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            PhoneKeyButton(
                label = stringResource(R.string.old_phone_delete),
                sublabel = null,
                onClick = ::onDelete,
            )
        }
    }
}

@Composable
private fun PhoneScreen(
    value: String,
    modifier: Modifier = Modifier,
) {
    val screenDescription = stringResource(R.string.old_phone_display_accessibility)
    val cursorAlpha by rememberInfiniteTransition(label = "cursor").animateFloat(
        initialValue = 1f,
        targetValue = 1f,
        animationSpec =
            infiniteRepeatable(
                animation =
                    keyframes {
                        durationMillis = 1000
                        1f at 0
                        0f at 500
                    },
            ),
        label = "cursorAlpha",
    )
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier =
            modifier
                .heightIn(min = 72.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(PhoneScreen)
                .border(3.dp, Color.Black.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                .padding(12.dp)
                .semantics { contentDescription = screenDescription },
    ) {
        Text(
            text =
                buildAnnotatedString {
                    append(value)
                    withStyle(SpanStyle(color = PhoneScreenText.copy(alpha = cursorAlpha))) {
                        append(
                            "_",
                        )
                    }
                },
            style =
                DefaultTextStyles.bodyBig.copy(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 22.sp,
                    color = PhoneScreenText,
                    textAlign = TextAlign.Start,
                ),
            overflow = TextOverflow.Clip,
        )
    }
}

@Composable
private fun RowScope.PhoneKeyButton(
    label: String,
    sublabel: String?,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier =
            Modifier
                .weight(1f)
                .defaultMinSize(minHeight = 56.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(PhoneKey)
                .clickable(role = Role.Button, onClick = onClick)
                .padding(vertical = 6.dp),
    ) {
        Text(
            text = label,
            style = DefaultTextStyles.bodyBig.copy(fontSize = 22.sp, color = PhoneKeyText),
        )
        if (sublabel != null) {
            Text(
                text = sublabel,
                style = DefaultTextStyles.body.copy(fontSize = 12.sp, color = PhoneKeyLetters),
            )
        }
    }
}

@Preview(name = "Téléphone vide")
@Composable
private fun OldPhoneEmptyPreview() {
    AppTheme {
        OldPhone(value = "", onValueChange = {}, modifier = Modifier.padding(16.dp))
    }
}

@Preview(name = "Téléphone interactif")
@Composable
private fun OldPhoneInteractivePreview() {
    var value by remember { mutableStateOf("") }
    AppTheme {
        OldPhone(value = value, onValueChange = { value = it }, modifier = Modifier.padding(16.dp))
    }
}
