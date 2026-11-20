package com.pklein.mariage.presentation.destinations.cambodge

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
import com.pklein.mariage.presentation.carnetBord.CarnetBordUniversActivity
import com.pklein.mariage.ui.screen.QuestionScreen
import com.pklein.mariage.ui.theme.annotatedStringResource
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts
import dagger.hilt.android.AndroidEntryPoint

private const val RESPONSE = "mangue"

@AndroidEntryPoint
class SalleDinerCambodgeFourFragment : Fragment() {
    private val gameStep = GameStep.SalleDinerCambodge4
    private val universStep = SharedPreferenceKey.UNIVERS_2_CAMBODGE
    private val gameProgress: GameProgressViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        gameProgress.storePage(gameStep)
        gameProgress.storeUniversPage(universStep, gameStep)

        return ComposeView(requireContext()).apply {
            setContent {
                QuestionScreen(
                    title = R.string.diner_titre_cambodge,
                    message = annotatedStringResource(id = R.string.diner_cambodge_four_question),
                    enigmeImage = R.drawable.image_parchemin_mangue,
                    enigmeImageDescription = stringResource(R.string.salle_diner_cambodge_mangue_accessibility),
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
            Alerts.showClue(it, getString(R.string.diner_cambodge_four_indice)) {
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
        context?.let {
            if (response.formatAnswer() == RESPONSE) {
                Alerts.showSuccess(it, gameProgress.playerName, gameProgress.gender, ::launchNext)
            } else {
                Alerts.showError(it) {
                    gameProgress.updateError()
                }
            }
        }
    }

    private fun launchNext() {
        findNavController().navigate(R.id.action_cambodge_four_to_five)
    }
}
