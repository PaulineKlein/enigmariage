package com.pklein.mariage.presentation.combatFinal.resultat.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pklein.mariage.R
import com.pklein.mariage.data.model.RoomPlayer
import com.pklein.mariage.data.repository.PlayerGender
import com.pklein.mariage.presentation.combatFinal.resultat.model.PodiumState
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.ColorHint
import com.pklein.mariage.ui.theme.ColorPrimaryDark
import com.pklein.mariage.ui.theme.DefaultTextStyles
import com.pklein.mariage.utils.formatDuration

private val CARD_CORNER_RADIUS = 24.dp
private const val CARD_BACKGROUND_ALPHA = 0.6f
private val CARD_PADDING = 16.dp
private val STEP_FIRST_HEIGHT = 88.dp
private val STEP_SECOND_HEIGHT = 64.dp
private val STEP_THIRD_HEIGHT = 44.dp
private val STEP_CORNER_RADIUS = 12.dp
private val RANK_WIDTH = 32.dp
private val TROPHY_ICON_SIZE = 16.dp
private val STEP_AVATAR_SIZE = 48.dp
private val ROW_AVATAR_SIZE = 32.dp
private const val OTHER_PLAYERS_SHOWN = 7
private const val PODIUM_SIZE = 3
private val MEDAL_GOLD = Color(0xFFE6B422)
private val MEDAL_SILVER = Color(0xFFB4BCC4)
private val MEDAL_BRONZE = Color(0xFFCD8B4F)

/** Podium of the joined room: the first three on steps, then the next ones in a list, and where the player stands. */
@Composable
fun RoomPodium(
    state: PodiumState,
    modifier: Modifier = Modifier,
) {
    if (state == PodiumState.Hidden) return

    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(CARD_CORNER_RADIUS))
                .background(Color.White.copy(alpha = CARD_BACKGROUND_ALPHA))
                .padding(CARD_PADDING),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        when (state) {
            PodiumState.Hidden -> {
                Unit
            }

            PodiumState.Loading -> {
                Text(
                    text = stringResource(R.string.resultat_podium_loading),
                    style = DefaultTextStyles.body,
                    textAlign = TextAlign.Center,
                )
            }

            PodiumState.Error -> {
                Text(
                    text = stringResource(R.string.resultat_podium_error),
                    style = DefaultTextStyles.body,
                    textAlign = TextAlign.Center,
                )
            }

            is PodiumState.Ready -> {
                PodiumContent(state)
            }
        }
    }
}

@Composable
private fun PodiumContent(state: PodiumState.Ready) {
    val players = state.players
    val myRank = players.indexOfFirst { it.isMe } + 1

    Text(
        text = stringResource(R.string.resultat_podium_title, state.roomCode),
        style = DefaultTextStyles.sousTitre.copy(color = ColorPrimaryDark),
        textAlign = TextAlign.Center,
    )

    // Second on the left, first in the middle, third on the right.
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Bottom,
    ) {
        listOf(1, 0, 2)
            .filter { it < players.size }
            .forEach { index ->
                PodiumStep(
                    rank = index + 1,
                    player = players[index],
                    modifier = Modifier.weight(1f),
                )
            }
    }

    players.drop(PODIUM_SIZE).take(OTHER_PLAYERS_SHOWN).forEachIndexed { index, player ->
        OtherPlayerRow(rank = PODIUM_SIZE + index + 1, player = player)
    }

    if (myRank > 0) {
        Text(
            text = stringResource(R.string.resultat_podium_rank, myRank, players.size),
            style = DefaultTextStyles.body.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun PodiumStep(
    rank: Int,
    player: RoomPlayer,
    modifier: Modifier = Modifier,
) {
    val (medalColor, stepHeight) =
        when (rank) {
            1 -> MEDAL_GOLD to STEP_FIRST_HEIGHT
            2 -> MEDAL_SILVER to STEP_SECOND_HEIGHT
            else -> MEDAL_BRONZE to STEP_THIRD_HEIGHT
        }
    val description =
        stringResource(
            R.string.resultat_podium_place_accessibility,
            rank,
            player.name,
            player.score,
            timeText(player),
            player.trophies,
        )

    Column(
        modifier = modifier.semantics(mergeDescendants = true) { contentDescription = description },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PlayerAvatar(player = player, size = STEP_AVATAR_SIZE)
        Text(
            text = displayName(player),
            style = DefaultTextStyles.body.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.clearAndSetSemantics {},
        )
        Text(
            text = player.score.toString(),
            style = DefaultTextStyles.bodyBig,
            modifier = Modifier.clearAndSetSemantics {},
        )
        TrophyCount(player.trophies)
        Text(
            text = timeText(player),
            style = DefaultTextStyles.body.copy(color = ColorHint),
            textAlign = TextAlign.Center,
            modifier = Modifier.clearAndSetSemantics {},
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier =
                Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
                    .height(stepHeight)
                    .clip(
                        RoundedCornerShape(
                            topStart = STEP_CORNER_RADIUS,
                            topEnd = STEP_CORNER_RADIUS,
                        ),
                    ).background(medalColor)
                    .clearAndSetSemantics {},
        ) {
            Text(
                text = rank.toString(),
                style = DefaultTextStyles.titre.copy(color = Color.White),
            )
        }
    }
}

@Composable
private fun OtherPlayerRow(
    rank: Int,
    player: RoomPlayer,
) {
    val description =
        stringResource(
            R.string.resultat_podium_place_accessibility,
            rank,
            player.name,
            player.score,
            timeText(player),
            player.trophies,
        )

    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(STEP_CORNER_RADIUS))
                .then(if (player.isMe) Modifier.background(Color.White) else Modifier)
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .semantics(mergeDescendants = true) { contentDescription = description },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(text = "$rank.", style = DefaultTextStyles.body, modifier = Modifier.width(RANK_WIDTH))
        PlayerAvatar(player = player, size = ROW_AVATAR_SIZE)
        Text(
            text = displayName(player),
            style = DefaultTextStyles.body,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
        )
        TrophyCount(player.trophies)
        Text(text = player.score.toString(), style = DefaultTextStyles.bodyBig)
    }
}

/** The inspector (dog or rabbit) the player picked; nothing for a player saved without one. */
@Composable
private fun PlayerAvatar(
    player: RoomPlayer,
    size: Dp,
    modifier: Modifier = Modifier,
) {
    val avatar =
        when (player.gender) {
            PlayerGender.DOG.name -> R.drawable.image_perso_dog
            PlayerGender.RABBIT.name -> R.drawable.image_perso_rabbit
            else -> return
        }
    Image(
        painter = painterResource(avatar),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = modifier.size(size).clearAndSetSemantics {},
    )
}

/** The trophy icon and how many the player unlocked, which breaks ties between equal scores. */
@Composable
private fun TrophyCount(count: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.clearAndSetSemantics {},
    ) {
        Image(
            painter = painterResource(R.drawable.ic_trophy),
            contentDescription = null,
            modifier = Modifier.size(TROPHY_ICON_SIZE),
        )
        Text(text = count.toString(), style = DefaultTextStyles.body)
    }
}

@Composable
private fun displayName(player: RoomPlayer): String =
    if (player.isMe) stringResource(R.string.resultat_podium_you, player.name) else player.name

private fun timeText(player: RoomPlayer): String = if (player.timeMillis > 0) formatDuration(player.timeMillis) else "–"

private val previewPlayers =
    listOf(
        RoomPlayer("1", "Alice", "RABBIT", 97, 1_500_000),
        RoomPlayer("2", "Chloé", "DOG", 91, 1_740_000, trophies = 18, isMe = true),
        RoomPlayer("3", "Bob", "DOG", 91, 1_320_000, trophies = 14),
        RoomPlayer("4", "Dimitri", "RABBIT", 80, 2_100_000),
        RoomPlayer("5", "Éva", "DOG", 62, 2_900_000),
    )

@Preview(showBackground = true, name = "Podium")
@Composable
private fun RoomPodiumPreview() {
    AppTheme {
        RoomPodium(
            state = PodiumState.Ready("MARIAGE", previewPlayers),
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(showBackground = true, name = "Podium - un seul joueur")
@Composable
private fun RoomPodiumSinglePreview() {
    AppTheme {
        RoomPodium(
            state = PodiumState.Ready("MARIAGE", previewPlayers.take(1)),
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(showBackground = true, name = "Podium - erreur")
@Composable
private fun RoomPodiumErrorPreview() {
    AppTheme {
        RoomPodium(state = PodiumState.Error, modifier = Modifier.padding(16.dp))
    }
}
