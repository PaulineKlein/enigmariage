package com.pklein.mariage.presentation.combatFinal.screen

import android.view.HapticFeedbackConstants
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pklein.mariage.R
import com.pklein.mariage.ui.atome.AppButton
import com.pklein.mariage.ui.atome.NoxImage
import com.pklein.mariage.ui.screen.MyNavigationBar
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.DefaultTextStyles
import com.pklein.mariage.ui.theme.annotatedStringResource

private val NOX_MAX_WIDTH = 320.dp
private val CONTENT_MAX_WIDTH = 480.dp
private const val NOX_MAX_TAP_COUNT = 5

@Composable
fun CombatFinalOneScreen(
    onHomeClick: () -> Unit,
    onContinueClick: () -> Unit,
    onNoxTapped: () -> Unit,
) {
    var tapCount by rememberSaveable { mutableIntStateOf(0) }
    val view = LocalView.current
    val onNoxClick: () -> Unit = {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
        tapCount++
        if (tapCount == NOX_MAX_TAP_COUNT) onNoxTapped()
    }

    AppTheme {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .safeDrawingPadding()
                    .padding(16.dp),
        ) {
            MyNavigationBar(
                title = R.string.combatFinal_titre,
                onHomeClick = onHomeClick,
            )

            BoxWithConstraints(
                modifier =
                    Modifier
                        .weight(1f)
                        .fillMaxWidth(),
            ) {
                if (maxWidth > maxHeight) {
                    LandscapeContent(
                        tapCount = tapCount,
                        onNoxClick = onNoxClick,
                        onContinueClick = onContinueClick,
                    )
                } else {
                    PortraitContent(
                        tapCount = tapCount,
                        onNoxClick = onNoxClick,
                        onContinueClick = onContinueClick,
                    )
                }
            }
        }
    }
}

@Composable
private fun PortraitContent(
    tapCount: Int,
    onNoxClick: () -> Unit,
    onContinueClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        BoxWithConstraints(
            modifier =
                Modifier
                    .weight(1f)
                    .fillMaxWidth(),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .heightIn(min = maxHeight),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                NoxMessage()
                NoxImage(
                    tapCount = tapCount,
                    maxTapCount = NOX_MAX_TAP_COUNT,
                    onClick = onNoxClick,
                    modifier =
                        Modifier
                            .widthIn(max = NOX_MAX_WIDTH)
                            .fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        ContinueButton(
            onClick = onContinueClick,
            modifier =
                Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally)
                    .widthIn(max = CONTENT_MAX_WIDTH)
                    .fillMaxWidth(0.7f),
        )
    }
}

@Composable
private fun LandscapeContent(
    tapCount: Int,
    onNoxClick: () -> Unit,
    onContinueClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            NoxMessage()
            ContinueButton(
                onClick = onContinueClick,
                modifier =
                    Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(0.8f),
            )
        }
        Box(
            modifier =
                Modifier
                    .weight(1f)
                    .fillMaxHeight(),
            contentAlignment = Alignment.Center,
        ) {
            NoxImage(
                tapCount = tapCount,
                maxTapCount = NOX_MAX_TAP_COUNT,
                onClick = onNoxClick,
            )
        }
    }
}

@Composable
private fun NoxMessage() {
    Text(
        text = annotatedStringResource(id = R.string.combatFinal_one_message),
        style = DefaultTextStyles.bodyBig,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun ContinueButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppButton(
        text = stringResource(id = R.string.continuer),
        onClick = onClick,
        modifier = modifier,
    )
}

@Preview(showSystemUi = true, name = "Combat final 1")
@Composable
private fun CombatFinalOneScreenPreview() {
    CombatFinalOneScreen(
        onHomeClick = {},
        onContinueClick = {},
        onNoxTapped = {},
    )
}

@Preview(showSystemUi = true, name = "Combat final 1 - paysage", widthDp = 800, heightDp = 360)
@Composable
private fun CombatFinalOneScreenLandscapePreview() {
    CombatFinalOneScreen(
        onHomeClick = {},
        onContinueClick = {},
        onNoxTapped = {},
    )
}
