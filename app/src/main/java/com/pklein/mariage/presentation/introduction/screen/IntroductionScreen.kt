package com.pklein.mariage.presentation.introduction.screen

import android.content.res.Configuration
import android.view.HapticFeedbackConstants
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pklein.mariage.R
import com.pklein.mariage.ui.atome.AppButton
import com.pklein.mariage.ui.atome.NoxImage
import com.pklein.mariage.ui.atome.PagerDots
import com.pklein.mariage.ui.modifier.pulse
import com.pklein.mariage.ui.modifier.zoomOutTransform
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.ColorBleuFonce
import com.pklein.mariage.ui.theme.DefaultTextStyles
import com.pklein.mariage.ui.theme.annotatedStringResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

private const val PAGE_COUNT = 3
private const val NOX_MAX_COUNT = 5
private val NOX_HEIGHT = 200.dp
private const val IMAGE_MAX_HEIGHT_RATIO = 0.5f
private val IMAGE_CORNER_RADIUS = 12.dp
private val BOTTOM_BAR_HEIGHT = 48.dp
private const val START_BUTTON_WIDTH_RATIO = 0.7f
private val TYPEWRITER_CHAR_DELAY = 24.milliseconds
private const val TYPEWRITER_CHARS_PER_STEP = 1
private val TYPEWRITER_PAUSE = 200.milliseconds
private const val TYPEWRITER_PAUSE_CHARS = ".!?…"
private val ELIXIR_DOTS_DELAY = 400.milliseconds
private const val ELIXIR_MAX_DOTS = 3
private const val ARROW_NUDGE_DP = 8f
private const val ARROW_NUDGE_DURATION_MS = 700

/** Three swipeable story pages (Nox, the mayor, the couple + start button), replaces activity_introduction.xml. */
@Composable
fun IntroductionScreen(
    showSkip: Boolean,
    onNoxTapped: () -> Unit,
    onStartClick: () -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { PAGE_COUNT })

    AppTheme {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .safeDrawingPadding()
                    .padding(24.dp),
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f).fillMaxWidth(),
            ) { page ->
                Box(modifier = Modifier.fillMaxSize().zoomOutTransform(pagerState, page)) {
                    when (page) {
                        0 -> {
                            NoxPage(
                                active = pagerState.currentPage == page,
                                onNoxTapped = onNoxTapped,
                            )
                        }

                        1 -> {
                            StoryPage(
                                text = annotatedStringResource(R.string.introduction_maire),
                                image = R.drawable.image_maire,
                                imageDescription = stringResource(R.string.introduction_maire_image),
                                active = pagerState.currentPage == page,
                            )
                        }

                        else -> {
                            StoryPage(
                                text = annotatedStringResource(R.string.introduction_Bottom),
                                image = R.drawable.image_couple,
                                imageDescription = stringResource(R.string.introduction_couple_image),
                                active = pagerState.currentPage == page,
                                showElixirHint = true,
                                onStartClick = onStartClick,
                            )
                        }
                    }
                }
            }

            IntroBottomBar(pagerState = pagerState, showSkip = showSkip, onSkip = onStartClick)
        }
    }
}

/** Dots in the middle, "Passer" on the left and a nudging arrow on the right; both fade out on the last page. */
@Composable
private fun IntroBottomBar(
    pagerState: PagerState,
    showSkip: Boolean,
    onSkip: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val showNavigation = pagerState.currentPage < PAGE_COUNT - 1

    Box(
        modifier = Modifier.fillMaxWidth().height(BOTTOM_BAR_HEIGHT),
        contentAlignment = Alignment.Center,
    ) {
        PagerDots(pageCount = PAGE_COUNT, currentPage = pagerState.currentPage)

        AnimatedVisibility(
            visible = showNavigation && showSkip,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.CenterStart),
        ) {
            TextButton(onClick = onSkip) {
                Text(
                    text = stringResource(R.string.introduction_passer),
                    style = DefaultTextStyles.body.copy(color = ColorBleuFonce),
                )
            }
        }

        AnimatedVisibility(
            visible = showNavigation,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.CenterEnd),
        ) {
            NextArrow(
                onClick = {
                    scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                },
            )
        }
    }
}

@Composable
private fun NextArrow(onClick: () -> Unit) {
    val transition = rememberInfiniteTransition(label = "nextArrow")
    val nudge by transition.animateFloat(
        initialValue = 0f,
        targetValue = ARROW_NUDGE_DP,
        animationSpec =
            infiniteRepeatable(
                animation =
                    tween(
                        durationMillis = ARROW_NUDGE_DURATION_MS,
                        easing = FastOutSlowInEasing,
                    ),
                repeatMode = RepeatMode.Reverse,
            ),
        label = "nextArrowNudge",
    )

    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(R.drawable.ic_arrow_next),
            contentDescription = stringResource(R.string.introduction_suivant),
            tint = ColorBleuFonce,
            modifier =
                Modifier
                    .size(32.dp)
                    .graphicsLayer { translationX = nudge.dp.toPx() },
        )
    }
}

@Composable
private fun NoxPage(
    active: Boolean,
    onNoxTapped: () -> Unit,
) {
    val paragraphs =
        listOf(
            annotatedStringResource(R.string.introduction_top),
            annotatedStringResource(R.string.introduction_top_2),
        )
    val revealed = rememberRevealedCount(paragraphs, active)
    var tapCount by rememberSaveable { mutableIntStateOf(0) }
    val view = LocalView.current
    val onNoxClick: () -> Unit = {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
        tapCount++
        if (tapCount == NOX_MAX_COUNT) onNoxTapped()
    }

    if (isLandscape()) {
        Row(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                IntroTitle()
                Spacer(modifier = Modifier.height(16.dp))
                TypewriterText(paragraphs, revealed)
            }
            Box(
                modifier = Modifier.weight(1f).fillMaxHeight(),
                contentAlignment = Alignment.Center,
            ) {
                NoxImage(tapCount = tapCount, maxTapCount = NOX_MAX_COUNT, onClick = onNoxClick)
            }
        }
    } else {
        PortraitColumn(arrangement = Arrangement.SpaceEvenly) {
            IntroTitle()
            TypewriterText(paragraphs, revealed)
            NoxImage(
                tapCount = tapCount,
                maxTapCount = NOX_MAX_COUNT,
                onClick = onNoxClick,
                modifier = Modifier.padding(top = 24.dp).height(NOX_HEIGHT),
            )
        }
    }
}

@Composable
private fun StoryPage(
    text: AnnotatedString,
    image: Int,
    imageDescription: String,
    active: Boolean,
    showElixirHint: Boolean = false,
    onStartClick: (() -> Unit)? = null,
) {
    val paragraphs = listOf(text)
    val revealed = rememberRevealedCount(paragraphs, active)

    if (isLandscape()) {
        Row(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TypewriterText(paragraphs, revealed)
                if (showElixirHint) {
                    ElixirHint(active = active, modifier = Modifier.padding(top = 16.dp))
                }
                if (onStartClick != null) {
                    Spacer(modifier = Modifier.height(24.dp))
                    StartButton(onClick = onStartClick)
                }
            }
            Box(
                modifier = Modifier.weight(1f).fillMaxHeight(),
                contentAlignment = Alignment.Center,
            ) {
                StoryImage(image = image, description = imageDescription)
            }
        }
    } else {
        PortraitColumn(arrangement = Arrangement.Center) { maxHeight ->
            TypewriterText(paragraphs, revealed)
            StoryImage(
                image = image,
                description = imageDescription,
                modifier =
                    Modifier
                        .padding(top = 32.dp)
                        .heightIn(max = maxHeight * IMAGE_MAX_HEIGHT_RATIO),
            )
            if (showElixirHint) {
                ElixirHint(active = active, modifier = Modifier.padding(top = 16.dp))
            }
            if (onStartClick != null) {
                Spacer(modifier = Modifier.height(24.dp))
                StartButton(
                    onClick = onStartClick,
                    modifier = Modifier.padding(top = 24.dp),
                )
            }
        }
    }
}

/** "Nox prépare son élixir…" with dots cycling, hinting that the countdown is about to start. */
@Composable
private fun ElixirHint(
    active: Boolean,
    modifier: Modifier = Modifier,
) {
    val label = stringResource(R.string.introduction_elixir_hint)
    var dotCount by remember { mutableIntStateOf(0) }

    LaunchedEffect(active) {
        while (active) {
            delay(ELIXIR_DOTS_DELAY)
            dotCount = (dotCount + 1) % (ELIXIR_MAX_DOTS + 1)
        }
    }

    // All dots are always laid out, the missing ones are transparent, so the line doesn't change width.
    val text =
        buildAnnotatedString {
            append(label)
            append(".".repeat(ELIXIR_MAX_DOTS))
            if (dotCount < ELIXIR_MAX_DOTS) {
                addStyle(
                    SpanStyle(color = Color.Transparent),
                    start = label.length + dotCount,
                    end = label.length + ELIXIR_MAX_DOTS,
                )
            }
        }

    Text(
        text = text,
        style = DefaultTextStyles.body.copy(fontStyle = FontStyle.Italic),
        textAlign = TextAlign.Center,
        modifier = modifier.fillMaxWidth().clearAndSetSemantics { contentDescription = label },
    )
}

/** Sized to the picture's own aspect ratio, so the rounded corners hug the visible image and not its letterbox. */
@Composable
private fun StoryImage(
    image: Int,
    description: String,
    modifier: Modifier = Modifier,
) {
    val painter = painterResource(image)
    val ratio = painter.intrinsicSize.run { width / height }

    Image(
        painter = painter,
        contentDescription = description,
        contentScale = ContentScale.Fit,
        modifier =
            modifier
                .aspectRatio(ratio)
                .clip(RoundedCornerShape(IMAGE_CORNER_RADIUS)),
    )
}

/** Call to action with a gentle pulse to draw the eye. */
@Composable
private fun StartButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppButton(
        text = stringResource(R.string.introduction_start),
        onClick = onClick,
        modifier =
            modifier
                .fillMaxWidth(START_BUTTON_WIDTH_RATIO)
                .pulse(),
    )
}

/** Scrollable, vertically arranged column that fills the page height, so small screens and big fonts never clip. */
@Composable
private fun PortraitColumn(
    arrangement: Arrangement.Vertical,
    content: @Composable (maxHeight: Dp) -> Unit,
) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val pageHeight = maxHeight
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .heightIn(min = pageHeight),
            verticalArrangement = arrangement,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            content(pageHeight)
        }
    }
}

@Composable
private fun IntroTitle() {
    Text(
        text = stringResource(R.string.introduction_titre),
        style = DefaultTextStyles.titre,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
    )
}

/**
 * Number of characters revealed so far, typed one by one while [active] (i.e. the page is visible).
 * Call it above any portrait/landscape branch so the saved progress survives a rotation.
 */
@Composable
private fun rememberRevealedCount(
    paragraphs: List<AnnotatedString>,
    active: Boolean,
): MutableIntState {
    val fullText = paragraphs.joinToString(separator = "") { it.text }
    val revealed = rememberSaveable { mutableIntStateOf(0) }

    LaunchedEffect(fullText, active) {
        while (active && revealed.intValue < fullText.length) {
            // Several characters per step: a delay below one frame (~16 ms) can't make the text appear faster.
            var isPause = false
            var step = 0
            while (step < TYPEWRITER_CHARS_PER_STEP && !isPause && revealed.intValue < fullText.length) {
                isPause = fullText[revealed.intValue] in TYPEWRITER_PAUSE_CHARS
                revealed.intValue++
                step++
            }
            delay(if (isPause) TYPEWRITER_PAUSE else TYPEWRITER_CHAR_DELAY)
        }
    }
    return revealed
}

/** Paragraphs typed out one after the other; tapping the text reveals everything at once. */
@Composable
private fun TypewriterText(
    paragraphs: List<AnnotatedString>,
    revealed: MutableIntState,
) {
    Column(
        modifier =
            Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) { revealed.intValue = paragraphs.sumOf { it.length } },
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        var offset = 0
        paragraphs.forEach { paragraph ->
            IntroText(paragraph.revealUpTo(revealed.intValue - offset))
            offset += paragraph.length
        }
    }
}

// The full text is always laid out, the unrevealed part is only transparent, so nothing shifts while typing.
private fun AnnotatedString.revealUpTo(count: Int): AnnotatedString =
    if (count >= length) {
        this
    } else {
        buildAnnotatedString {
            append(this@revealUpTo)
            addStyle(SpanStyle(color = Color.Transparent), start = maxOf(count, 0), end = length)
        }
    }

@Composable
private fun IntroText(text: AnnotatedString) {
    Text(
        text = text,
        style = DefaultTextStyles.body,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun isLandscape() = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

@Preview(name = "Introduction", showSystemUi = true)
@Composable
private fun IntroductionScreenPreview() {
    IntroductionScreen(showSkip = true, onNoxTapped = {}, onStartClick = {})
}
