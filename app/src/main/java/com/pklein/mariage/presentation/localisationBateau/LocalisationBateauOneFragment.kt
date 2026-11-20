package com.pklein.mariage.presentation.localisationBateau

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.pklein.mariage.R
import com.pklein.mariage.data.model.SharedPreferenceKey
import com.pklein.mariage.data.model.Trophies
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.carnetBord.CarnetBordActivity
import com.pklein.mariage.ui.screen.QuestionScreen
import com.pklein.mariage.ui.theme.annotatedStringResource
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocalisationBateauOneFragment : Fragment() {
    private val gameStep = GameStep.LocalisationBateau1
    private val universStep = SharedPreferenceKey.UNIVERS_1
    private val gameProgress: GameProgressViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        gameProgress.storePage(gameStep)
        gameProgress.storeUniversPage(universStep, gameStep)

        val description = gameProgress.getDescription(gameStep)
        val enigme = gameProgress.getEnigme(gameStep)
        val enigmeAccessibility = gameProgress.getEnigmeAccessibility(gameStep)
        val clue = gameProgress.getClue(gameStep)

        if (description == null || enigme == null || enigmeAccessibility == null || clue == null) {
            throw Exception("Informations cannot be null")
        }

        return ComposeView(requireContext()).apply {
            setContent {
                QuestionScreen(
                    title = R.string.diner_titre_localisation,
                    message = annotatedStringResource(id = description),
                    enigmeImage = enigme,
                    enigmeImageDescription = stringResource(enigmeAccessibility),
                    onHomeClick = {
                        startActivity(Intent(requireContext(), CarnetBordActivity::class.java))
                    },
                    onClueClick = { onClueClicked(clue) },
                    onValidateClick = ::onValidateClicked,
                )
            }
        }
    }

    private fun onClueClicked(clue: Int) {
        context?.let {
            Alerts.showClue(it, getString(clue)) {
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
                    Trophies.TROPHY_CLUE_1,
                )
            }
        }
    }

    private fun onValidateClicked(response: String) {
        context?.let { ctx ->
            val responseStep = gameProgress.getReponse(gameStep)?.let { getString(it) }

            if (responseStep != null && response.formatAnswer().contains(responseStep)) {
                Alerts.showSuccess(
                    ctx,
                    gameProgress.playerName,
                    gameProgress.gender,
                    ::launchNext,
                )
            } else {
                Alerts.showError(ctx) {
                    gameProgress.updateError()
                }
            }
        }
    }

    private fun launchNext() {
        findNavController().navigate(R.id.action_localisation_one_to_two)
    }
}
