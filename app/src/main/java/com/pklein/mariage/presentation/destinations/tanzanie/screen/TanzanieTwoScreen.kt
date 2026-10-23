package com.pklein.mariage.presentation.destinations.tanzanie.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pklein.mariage.R
import com.pklein.mariage.presentation.destinations.tanzanie.data.ANIMALS
import com.pklein.mariage.presentation.destinations.tanzanie.screen.atome.AnimalCell
import com.pklein.mariage.presentation.destinations.tanzanie.screen.atome.FootprintCell
import com.pklein.mariage.ui.atome.AppButton
import com.pklein.mariage.ui.atome.ParcheminCard
import com.pklein.mariage.ui.screen.MyNavigationBar
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.ColorHint
import com.pklein.mariage.ui.theme.DefaultTextStyles
import com.pklein.mariage.ui.theme.annotatedStringResource

private val FOOTPRINT_ORDER = listOf(2, 0, 4, 1, 3)
private val ANIMAL_ORDER = listOf(0, 3, 1, 4, 2)
private const val NO_MATCH = -1

/** Reliées : matches[idEmpreinte] = idAnimal, ou NO_MATCH. Bonne réponse : matches[i] == i. */
@Composable
fun TanzanieTwoScreen(
    onHomeClick: () -> Unit,
    onValidateClick: (List<Int>) -> Unit,
) {
    var matches by rememberSaveable { mutableStateOf(IntArray(ANIMALS.size) { NO_MATCH }) }
    var selected by rememberSaveable { mutableStateOf<Int?>(null) }
    val isComplete = matches.none { it == NO_MATCH }

    fun onFootprintClick(footprint: Int) {
        if (selected == footprint) {
            selected = null
            return
        }
        matches = matches.copyOf().also { it[footprint] = NO_MATCH }
        selected = footprint
    }

    fun onAnimalClick(animal: Int) {
        val footprint = selected
        if (footprint == null) {
            val linked = matches.indexOf(animal)
            if (linked != NO_MATCH) matches = matches.copyOf().also { it[linked] = NO_MATCH }
        } else {
            matches =
                matches.copyOf().also { result ->
                    result.indices.filter { result[it] == animal }.forEach { result[it] = NO_MATCH }
                    result[footprint] = animal
                }
            selected = null
        }
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .imePadding()
                .padding(horizontal = 16.dp, vertical = 16.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
        ) {
            MyNavigationBar(
                title = R.string.diner_titre_tanzanie,
                onHomeClick = onHomeClick,
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = annotatedStringResource(id = R.string.diner_tanzanie_two_message),
                style = DefaultTextStyles.body,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(24.dp))

            ParcheminCard(
                text = stringResource(id = R.string.diner_tanzanie_two_enigme),
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(24.dp))

            FOOTPRINT_ORDER.indices.forEach { position ->
                val footprint = FOOTPRINT_ORDER[position]
                val animal = ANIMAL_ORDER[position]
                val linkedFootprint = matches.indexOf(animal)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                ) {
                    FootprintCell(
                        animal = ANIMALS[footprint],
                        badge = if (matches[footprint] != NO_MATCH) pairNumber(footprint) else null,
                        isSelected = selected == footprint,
                        description =
                            stringResource(
                                R.string.diner_tanzanie_two_empreinte_accessibility,
                                position + 1,
                            ),
                        onClick = { onFootprintClick(footprint) },
                        modifier = Modifier.weight(1f),
                    )
                    AnimalCell(
                        animal = ANIMALS[animal],
                        badge = if (linkedFootprint != NO_MATCH) pairNumber(linkedFootprint) else null,
                        onClick = { onAnimalClick(animal) },
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 24.dp),
        ) {
            AppButton(
                text = stringResource(id = R.string.valider),
                onClick = { onValidateClick(matches.toList()) },
                enabled = isComplete,
                modifier = Modifier.weight(1f),
            )
            TextButton(
                onClick = {
                    matches = IntArray(ANIMALS.size) { NO_MATCH }
                    selected = null
                },
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = stringResource(id = R.string.recommencer),
                    style = DefaultTextStyles.body.copy(color = ColorHint),
                )
            }
        }
    }
}

private fun pairNumber(footprint: Int): Int = FOOTPRINT_ORDER.indexOf(footprint) + 1

@Preview(showBackground = true, name = "Tanzanie 2")
@Composable
private fun TanzanieTwoScreenPreview() {
    AppTheme {
        TanzanieTwoScreen(
            onHomeClick = {},
            onValidateClick = {},
        )
    }
}
