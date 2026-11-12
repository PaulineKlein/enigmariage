package com.pklein.mariage.presentation.destinations.tanzanie.screen.atome

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pklein.mariage.R
import com.pklein.mariage.presentation.destinations.tanzanie.data.Animal
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.DefaultTextStyles
import com.pklein.mariage.ui.theme.annotatedStringResource

@Composable
fun AnimalCell(
    animal: Animal,
    badge: Int?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
            modifier
                .semantics(mergeDescendants = true) {}
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    role = Role.Button,
                    onClick = onClick,
                ),
    ) {
        Box {
            Image(
                painter = painterResource(animal.photo),
                contentDescription = stringResource(animal.name),
                contentScale = ContentScale.Fit,
                modifier = Modifier.cellFrame(isSelected = badge != null),
            )
            badge?.let { PairBadge(it, Modifier.align(Alignment.TopEnd)) }
        }
        Text(
            text = annotatedStringResource(animal.name),
            style = DefaultTextStyles.body,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AnimalCellPreview() {
    AppTheme {
        AnimalCell(
            animal =
                Animal(
                    R.drawable.image_empreinte_lion,
                    R.drawable.image_empreinte_lion_soluce,
                    R.string.diner_tanzanie_two_name_swahili_lion,
                    R.string.diner_tanzanie_two_name_lion,
                ),
            badge = 3,
            onClick = {},
        )
    }
}
