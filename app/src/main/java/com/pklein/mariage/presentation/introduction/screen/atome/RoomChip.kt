package com.pklein.mariage.presentation.introduction.screen.atome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pklein.mariage.R
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.DefaultTextStyles
import com.pklein.mariage.ui.theme.GradientEndColor

private val ROOM_CHIP_CORNER_RADIUS = 16.dp
private const val ROOM_CHIP_BACKGROUND_ALPHA = 0.6f

/** Which room the saved game belongs to, as a small pill under "Reprendre", with an icon beside it to leave it. */
@Composable
fun RoomChip(
    roomCode: String,
    onLeaveRoom: () -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = stringResource(R.string.splash_room, roomCode),
            style = DefaultTextStyles.body,
            modifier =
                Modifier
                    .clip(RoundedCornerShape(ROOM_CHIP_CORNER_RADIUS))
                    .background(GradientEndColor.copy(alpha = ROOM_CHIP_BACKGROUND_ALPHA))
                    .padding(horizontal = 12.dp, vertical = 6.dp),
        )
        IconButton(onClick = onLeaveRoom) {
            Icon(
                painter = painterResource(R.drawable.ic_leave),
                contentDescription = stringResource(R.string.splash_leave_room),
                tint = DefaultTextStyles.body.color,
            )
        }
    }
}

@Preview(name = "Pastille de salle", widthDp = 320)
@Composable
private fun RoomChipPreview() {
    AppTheme {
        RoomChip(roomCode = "K7M2QX", onLeaveRoom = {})
    }
}
