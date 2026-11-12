package com.pklein.mariage.presentation.carnetBord.screen

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pklein.mariage.R
import com.pklein.mariage.data.repository.UniversStatus
import com.pklein.mariage.ui.atome.AppButton
import com.pklein.mariage.ui.screen.MyNavigationBar
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.ColorBleuFonce
import com.pklein.mariage.ui.theme.DefaultTextStyles

private val SCREEN_PADDING = 16.dp
private val MAP_TOP_SPACING = 24.dp
private val SUBTITLE_TOP_SPACING = 4.dp
private val LANDSCAPE_COLUMNS_SPACING = 16.dp
private val MAP_MAX_WIDTH = 600.dp
private const val MAP_ASPECT_RATIO = 567f / 320f
private val MAP_ELEVATION = 6.dp
private val MAP_SHAPE = RoundedCornerShape(12.dp)
private val MAP_SHADOW_COLOR = ColorBleuFonce.copy(alpha = 0.35f)
private val MESSAGE_TOP_SPACING = 24.dp
private val BUTTONS_TOP_SPACING = 8.dp
private val BRAVO_TOP_SPACING = 32.dp
private const val CELEBRATION_START_SCALE = 0.8f
private val CELEBRATION_SPACING = 12.dp
private val CELEBRATION_EMOJI_SIZE = 22.sp
private val BUTTON_MAX_WIDTH = 300.dp

/** One destination of the second universe: its button and the part of the world map it lights up once finished. */
data class DestinationState(
    val card: UniversCardState,
    @DrawableRes val mapOverlay: Int,
)

/** World map with one button per destination, replaces activity_carnet_bord_univers.xml and item_univers_button.xml. */
@Composable
fun CarnetBordUniversScreen(
    destinations: List<DestinationState>,
    playerName: String?,
    allFinished: Boolean,
    onHomeClick: () -> Unit,
    onUniversClick: (step: String) -> Unit,
    onCheckShown: (id: String) -> Unit,
    onBravoClick: () -> Unit,
) {
    val landscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    val map: @Composable () -> Unit = { WorldMap(destinations) }
    val choices: @Composable () -> Unit = {
        ChoicesSection(
            destinations,
            allFinished,
            onUniversClick,
            onCheckShown,
            onBravoClick,
        )
    }

    AppTheme {
        if (landscape) {
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .safeDrawingPadding()
                        .padding(SCREEN_PADDING),
            ) {
                Header(allFinished, playerName, onHomeClick)
                Row(
                    modifier =
                        Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(LANDSCAPE_COLUMNS_SPACING),
                ) {
                    CenteredScrollColumn(
                        Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                    ) { map() }
                    CenteredScrollColumn(
                        Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                    ) { choices() }
                }
            }
        } else {
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .safeDrawingPadding()
                        .verticalScroll(rememberScrollState())
                        .padding(SCREEN_PADDING),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Header(allFinished, playerName, onHomeClick)
                Spacer(Modifier.height(MAP_TOP_SPACING))
                map()
                choices()
            }
        }
    }
}

@Composable
private fun Header(
    allFinished: Boolean,
    playerName: String?,
    onHomeClick: () -> Unit,
) {
    val playerName = playerName.orEmpty()
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        MyNavigationBar(title = R.string.carnet_bord2_titre, onHomeClick = onHomeClick)
        Spacer(Modifier.height(MESSAGE_TOP_SPACING))
        Text(
            // One line that invites to choose while destinations remain, then just keeps the mystery.
            text =
                if (allFinished) {
                    stringResource(R.string.carnet_bord2_sous_titre_termine, playerName)
                } else {
                    stringResource(R.string.carnet_bord2_sous_titre)
                },
            style = DefaultTextStyles.sousTitre,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = SUBTITLE_TOP_SPACING),
        )
    }
}

// Scrollable so small screens never clip, yet centered when there is room.
@Composable
private fun CenteredScrollColumn(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    BoxWithConstraints(modifier = modifier) {
        val availableHeight = maxHeight
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .heightIn(min = availableHeight),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            content = content,
        )
    }
}

@Composable
private fun ChoicesSection(
    destinations: List<DestinationState>,
    allFinished: Boolean,
    onUniversClick: (step: String) -> Unit,
    onCheckShown: (id: String) -> Unit,
    onBravoClick: () -> Unit,
) {
    Spacer(Modifier.height(MESSAGE_TOP_SPACING))
    destinations.forEach { destination ->
        UniversButton(
            state = destination.card,
            onClick = { onUniversClick(destination.card.step) },
            horizontal = true,
            onCheckShown = { onCheckShown(destination.card.id) },
            modifier =
                Modifier
                    .padding(top = BUTTONS_TOP_SPACING)
                    .widthIn(max = BUTTON_MAX_WIDTH)
                    .fillMaxWidth(),
        )
    }

    if (allFinished) {
        Celebration(
            onClick = onBravoClick,
            modifier =
                Modifier
                    .padding(top = BRAVO_TOP_SPACING)
                    .widthIn(max = BUTTON_MAX_WIDTH)
                    .fillMaxWidth(),
        )
    }
}

/** Pops in once everything is found: a congratulation and the way back to the logbook, straight on the background. */
@Composable
private fun Celebration(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val popIn = remember { Animatable(CELEBRATION_START_SCALE) }
    LaunchedEffect(Unit) {
        popIn.animateTo(1f, spring(dampingRatio = Spring.DampingRatioMediumBouncy))
    }

    Column(
        modifier =
            modifier.graphicsLayer {
                scaleX = popIn.value
                scaleY = popIn.value
                alpha = popIn.value.coerceIn(0f, 1f)
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(CELEBRATION_SPACING),
    ) {
        // Decorative: the message below carries the meaning.
        Text(
            text = "\uD83C\uDF89 \uD83C\uDFC6 \uD83C\uDF89",
            fontSize = CELEBRATION_EMOJI_SIZE,
            modifier = Modifier.clearAndSetSemantics { },
        )
        Text(
            text = stringResource(R.string.carnet_bord2_bravo),
            style = DefaultTextStyles.success,
            textAlign = TextAlign.Center,
        )
        AppButton(
            text = stringResource(R.string.carnet_bord2_button_home),
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

// Described by how many destinations are found, without saying which, as each button carries its own name.
@Composable
private fun WorldMap(destinations: List<DestinationState>) {
    val finished = destinations.count { it.card.status == UniversStatus.IS_FINISHED }
    val description =
        pluralStringResource(
            R.plurals.carnet_bord2_map_description,
            finished,
            finished,
            destinations.size,
        )

    Box(
        modifier =
            Modifier
                .widthIn(max = MAP_MAX_WIDTH)
                .fillMaxWidth()
                .aspectRatio(MAP_ASPECT_RATIO)
                .shadow(
                    MAP_ELEVATION,
                    MAP_SHAPE,
                    ambientColor = MAP_SHADOW_COLOR,
                    spotColor = MAP_SHADOW_COLOR,
                ),
    ) {
        Image(
            painter = painterResource(R.drawable.image_mapmonde),
            contentDescription = description,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize(),
        )
        destinations
            .filter { it.card.status == UniversStatus.IS_FINISHED }
            .forEach {
                Image(
                    painter = painterResource(it.mapOverlay),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize(),
                )
            }
    }
}

private val previewDestinations =
    listOf(
        DestinationState(
            UniversCardState(
                null,
                R.string.carnet_bord2_button_canada,
                UniversStatus.IS_FINISHED,
                false,
                "",
                "UNIVERS_2_CANADA",
            ),
            R.drawable.image_mapmonde_canada,
        ),
        DestinationState(
            UniversCardState(
                null,
                R.string.carnet_bord2_button_japon,
                UniversStatus.GOING,
                true,
                "",
                "UNIVERS_2_JAPON",
            ),
            R.drawable.image_mapmonde_japon,
        ),
        DestinationState(
            UniversCardState(
                null,
                R.string.carnet_bord2_button_cambodge,
                UniversStatus.GOING,
                true,
                "",
                "UNIVERS_2_CAMBODGE",
            ),
            R.drawable.image_mapmonde_cambodge,
        ),
    )

@Preview(name = "Mappemonde", showSystemUi = true)
@Composable
private fun CarnetBordUniversScreenPreview() {
    CarnetBordUniversScreen(
        destinations = previewDestinations,
        playerName = "Alice",
        allFinished = false,
        onHomeClick = {},
        onUniversClick = {},
        onCheckShown = {},
        onBravoClick = {},
    )
}

@Preview(name = "Mappemonde terminée", showSystemUi = true)
@Composable
private fun CarnetBordUniversScreenFinishedPreview() {
    CarnetBordUniversScreen(
        destinations =
            previewDestinations.map {
                it.copy(card = it.card.copy(status = UniversStatus.IS_FINISHED, clickable = false))
            },
        playerName = "Alice",
        allFinished = true,
        onHomeClick = {},
        onUniversClick = {},
        onCheckShown = {},
        onBravoClick = {},
    )
}

@Preview(name = "Mappemonde paysage", device = "spec:width=891dp,height=411dp")
@Composable
private fun CarnetBordUniversScreenLandscapePreview() {
    CarnetBordUniversScreen(
        destinations = previewDestinations,
        playerName = "Alice",
        allFinished = false,
        onHomeClick = {},
        onUniversClick = {},
        onCheckShown = {},
        onBravoClick = {},
    )
}
