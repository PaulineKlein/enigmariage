package com.pklein.mariage.presentation.localisationBateau.salleEmbarquement.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.pklein.mariage.R
import com.pklein.mariage.ui.atome.AppButton
import com.pklein.mariage.ui.modifier.pulse
import com.pklein.mariage.ui.screen.MyNavigationBar
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.ColorHelp
import com.pklein.mariage.ui.theme.ColorPrimary
import com.pklein.mariage.ui.theme.DefaultTextStyles
import com.pklein.mariage.ui.theme.annotatedStringResource

private const val PAWS_ANIMATION = "animation_paws.json"
private const val RESENDS_BEFORE_HELP = 2
private const val HELP_FADE_MS = 500
private val PAWS_SIZE = 150.dp
private val CONTENT_MAX_WIDTH = 480.dp
private val CARD_CORNER_RADIUS = 16.dp

@Composable
fun EmbarquementTwoNotificationScreen(
    onHomeClick: () -> Unit,
    onResendClick: () -> Unit,
    onHelpClick: () -> Unit,
) {
    var resendCount by rememberSaveable { mutableIntStateOf(0) }
    val showHelp = resendCount >= RESENDS_BEFORE_HELP
    val resend: () -> Unit = {
        resendCount++
        onResendClick()
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
                title = R.string.embarquement_one_titre,
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
                        resendCount = resendCount,
                        showHelp = showHelp,
                        onResendClick = resend,
                        onHelpClick = onHelpClick,
                    )
                } else {
                    PortraitContent(
                        resendCount = resendCount,
                        showHelp = showHelp,
                        onResendClick = resend,
                        onHelpClick = onHelpClick,
                    )
                }
            }
        }
    }
}

@Composable
private fun PortraitContent(
    resendCount: Int,
    showHelp: Boolean,
    onResendClick: () -> Unit,
    onHelpClick: () -> Unit,
) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .heightIn(min = maxHeight),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            NotificationTitle()
            ResendCard(onResendClick = onResendClick)
            HelpLink(visible = showHelp, onClick = onHelpClick)
            PawsAnimation(resendCount = resendCount)
        }
    }
}

@Composable
private fun LandscapeContent(
    resendCount: Int,
    showHelp: Boolean,
    onResendClick: () -> Unit,
    onHelpClick: () -> Unit,
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
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            NotificationTitle()
            ResendCard(onResendClick = onResendClick)
            HelpLink(visible = showHelp, onClick = onHelpClick)
        }
        Box(
            modifier =
                Modifier
                    .weight(0.6f)
                    .fillMaxHeight(),
            contentAlignment = Alignment.Center,
        ) {
            PawsAnimation(resendCount = resendCount)
        }
    }
}

@Composable
private fun NotificationTitle() {
    Text(
        text = stringResource(id = R.string.embarquement_two_notification_message_retry_1),
        style = DefaultTextStyles.sousTitre,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun ResendCard(onResendClick: () -> Unit) {
    Card(
        modifier =
            Modifier
                .widthIn(max = CONTENT_MAX_WIDTH)
                .fillMaxWidth(),
        shape = RoundedCornerShape(CARD_CORNER_RADIUS),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, ColorPrimary),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = R.string.embarquement_two_notification_message_retry_2),
                style = DefaultTextStyles.body,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(16.dp))
            AppButton(
                text = stringResource(id = R.string.renvoyer),
                onClick = onResendClick,
            )
        }
    }
}

/** Always laid out (so the page doesn't jump) but only visible and tappable once the player has resent enough. */
@Composable
private fun HelpLink(
    visible: Boolean,
    onClick: () -> Unit,
) {
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = HELP_FADE_MS),
        label = "helpLinkAlpha",
    )
    val shape = RoundedCornerShape(12.dp)

    Text(
        text = annotatedStringResource(id = R.string.embarquement_two_notification_message_retry_3),
        style = DefaultTextStyles.bodyLink,
        textAlign = TextAlign.Center,
        modifier =
            Modifier
                .graphicsLayer { this.alpha = alpha }
                .then(if (visible) Modifier.pulse() else Modifier)
                .clip(shape)
                .background(ColorHelp, shape)
                .then(
                    if (visible) {
                        Modifier.clickable(role = Role.Button, onClick = onClick)
                    } else {
                        Modifier
                    },
                ).padding(horizontal = 20.dp, vertical = 12.dp),
    )
}

/** Plays once on arrival and again each time the notification is resent. */
@Composable
private fun PawsAnimation(resendCount: Int) {
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset(PAWS_ANIMATION))
    val animatable = rememberLottieAnimatable()

    LaunchedEffect(composition, resendCount) {
        composition?.let { animatable.animate(it, iterations = 1) }
    }

    LottieAnimation(
        composition = composition,
        progress = { animatable.progress },
        modifier = Modifier.size(PAWS_SIZE),
    )
}

@Preview(showSystemUi = true, name = "Notification")
@Composable
private fun EmbarquementTwoNotificationScreenPreview() {
    EmbarquementTwoNotificationScreen(
        onHomeClick = {},
        onResendClick = {},
        onHelpClick = {},
    )
}

@Preview(showSystemUi = true, name = "Notification - paysage", widthDp = 800, heightDp = 360)
@Composable
private fun EmbarquementTwoNotificationScreenLandscapePreview() {
    EmbarquementTwoNotificationScreen(
        onHomeClick = {},
        onResendClick = {},
        onHelpClick = {},
    )
}
