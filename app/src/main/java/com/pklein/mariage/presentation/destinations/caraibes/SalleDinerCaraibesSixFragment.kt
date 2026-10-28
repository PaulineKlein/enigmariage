package com.pklein.mariage.presentation.destinations.caraibes

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.pklein.mariage.R
import com.pklein.mariage.data.model.SharedPreferenceKey
import com.pklein.mariage.data.model.Trophies
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.carnetBord.CarnetBordUniversActivity
import com.pklein.mariage.presentation.destinations.caraibes.screen.cassisSpots
import com.pklein.mariage.ui.atome.HelpCard
import com.pklein.mariage.ui.atome.ParcheminCard
import com.pklein.mariage.ui.screen.QuestionScreen
import com.pklein.mariage.ui.theme.annotatedStringResource
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts
import dagger.hilt.android.AndroidEntryPoint

private const val RESPONSE = "direction la tanzanie"

@AndroidEntryPoint
class SalleDinerCaraibesSixFragment : Fragment() {
    private val gameStep = GameStep.SalleDinerCaraibes6
    private val universStep = SharedPreferenceKey.UNIVERS_2_CARAIBES
    private val gameProgress: GameProgressViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        gameProgress.storePage(gameStep)
        gameProgress.storeUniversPage(universStep, gameStep)
        if (!gameProgress.isTrophyUnlocked(Trophies.TROPHY_DOG_COCO)) {
            gameProgress.saveTrophy(Trophies.TROPHY_DOG_COCO)
            context?.let {
                Alerts.showTrophyUnlocked(it, getString(Trophies.TROPHY_DOG_COCO.message))
            }
        }

        return ComposeView(requireContext()).apply {
            setContent {
                QuestionScreen(
                    title = R.string.diner_titre_caraibes,
                    message = annotatedStringResource(id = R.string.diner_caraibes_six_message),
                    enigmeContent = {
                        Column {
                            ParcheminCard(
                                text = stringResource(id = R.string.diner_caraibes_six_enigme),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.cassisSpots(),
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            HelpCard(text = annotatedStringResource(id = R.string.diner_caraibes_six_help))
                        }
                    },
                    onHomeClick = {
                        startActivity(
                            Intent(
                                requireContext(),
                                CarnetBordUniversActivity::class.java,
                            ),
                        )
                    },
                    onClueClick = ::onClueClicked,
                    onValidateClick = ::onValidateClicked,
                )
            }
        }
    }

    private fun onClueClicked() {
        context?.let {
            Alerts.showClue(it, getString(R.string.diner_caraibes_six_indice)) {
                updateClue()
            }
        }
    }

    private fun updateClue() {
        gameProgress.updateClue()
        if (!gameProgress.isTrophyUnlocked(Trophies.TROPHY_CLUE_1)) {
            gameProgress.saveTrophy(Trophies.TROPHY_CLUE_1)
            context?.let {
                Alerts.showTrophyUnlocked(
                    it,
                    getString(Trophies.TROPHY_CLUE_1.message),
                )
            }
        }
    }

    private fun onValidateClicked(response: String) {
        context?.let {
            if (response.formatAnswer() == RESPONSE) {
                Alerts.showSuccess(it, gameProgress.playerName, gameProgress.gender, ::launchNext)
            } else {
                Alerts.showError(it) { gameProgress.updateError() }
            }
        }
    }

    private fun launchNext() {
        gameProgress.finishUnivers(SharedPreferenceKey.UNIVERS_2_CARAIBES)
        activity?.startActivity(Intent(requireContext(), CarnetBordUniversActivity::class.java))
        activity?.finish()
    }
}
