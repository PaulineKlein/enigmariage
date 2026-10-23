package com.pklein.mariage.presentation.destinations.caraibes.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pklein.mariage.R
import com.pklein.mariage.ui.atome.AppButton
import com.pklein.mariage.ui.atome.ParcheminCard
import com.pklein.mariage.ui.screen.MyNavigationBar
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.ColorAccent
import com.pklein.mariage.ui.theme.ColorBleuFonce
import com.pklein.mariage.ui.theme.ColorHint
import com.pklein.mariage.ui.theme.ColorJusCoco
import com.pklein.mariage.ui.theme.DefaultTextStyles
import com.pklein.mariage.ui.theme.annotatedStringResource

private val CAPACITIES = intArrayOf(3, 5, 8)
private val INITIAL_AMOUNTS = intArrayOf(0, 0, 8)
private const val TARGET_FIOLE = 1
private const val TARGET_AMOUNT = 4
private val UNIT_HEIGHT = 24.dp
private val FIOLE_WIDTH = 80.dp

@Composable
fun CaraibesFiveScreen(
    onHomeClick: () -> Unit,
    onValidateClick: () -> Unit,
) {
    var amounts by rememberSaveable { mutableStateOf(INITIAL_AMOUNTS) }
    var selected by rememberSaveable { mutableStateOf<Int?>(null) }
    val isSolved = amounts[TARGET_FIOLE] == TARGET_AMOUNT

    fun onFioleClick(index: Int) {
        if (isSolved) return
        val from = selected
        when {
            from == null -> {
                if (amounts[index] > 0) selected = index
            }

            from == index -> {
                selected = null
            }

            else -> {
                val poured = minOf(amounts[from], CAPACITIES[index] - amounts[index])
                amounts =
                    amounts.copyOf().also {
                        it[from] -= poured
                        it[index] += poured
                    }
                selected = null
            }
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
                title = R.string.diner_titre_caraibes,
                onHomeClick = onHomeClick,
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = annotatedStringResource(id = R.string.diner_caraibes_five_message),
                style = DefaultTextStyles.body,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(24.dp))

            ParcheminCard(
                text = stringResource(id = R.string.diner_caraibes_five_recette),
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.fillMaxWidth(),
            ) {
                CAPACITIES.forEachIndexed { index, capacity ->
                    Fiole(
                        capacity = capacity,
                        amount = amounts[index],
                        isSelected = selected == index,
                        onClick = { onFioleClick(index) },
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
                onClick = onValidateClick,
                enabled = isSolved,
                modifier = Modifier.weight(1f),
            )
            TextButton(
                onClick = {
                    amounts = INITIAL_AMOUNTS
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

@Composable
private fun Fiole(
    capacity: Int,
    amount: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val description =
        stringResource(R.string.diner_caraibes_five_fiole_accessibility, capacity, amount)
    val shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
            Modifier
                .semantics(mergeDescendants = true) { contentDescription = description }
                .selectable(selected = isSelected, onClick = onClick, role = Role.Button),
    ) {
        Box(
            modifier =
                Modifier
                    .width(FIOLE_WIDTH)
                    .height(UNIT_HEIGHT * capacity)
                    .clip(shape)
                    .background(Color.White)
                    .border(
                        width = if (isSelected) 4.dp else 2.dp,
                        color = if (isSelected) ColorAccent else ColorBleuFonce,
                        shape = shape,
                    ),
            contentAlignment = Alignment.BottomCenter,
        ) {
            if (amount > 0) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(UNIT_HEIGHT * amount)
                            .background(ColorJusCoco),
                ) {
                    Text(
                        text = stringResource(R.string.quantite_litres, amount),
                        style = DefaultTextStyles.body,
                    )
                }
            }
        }
        Text(
            text = stringResource(R.string.quantite_litres, capacity),
            style = DefaultTextStyles.body,
            modifier = Modifier.padding(top = 4.dp),
        )
    }
}

@Preview(showBackground = true, name = "Réception 7")
@Composable
private fun CaraibesFiveScreenPreview() {
    AppTheme {
        CaraibesFiveScreen(
            onHomeClick = {},
            onValidateClick = {},
        )
    }
}
