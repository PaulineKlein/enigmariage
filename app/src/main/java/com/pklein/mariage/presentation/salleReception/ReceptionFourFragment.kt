package com.pklein.mariage.presentation.salleReception

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.pklein.mariage.R
import com.pklein.mariage.data.model.SharedPreferenceKey
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.carnetBord.CarnetBordActivity
import com.pklein.mariage.presentation.salleReception.screen.data.ReceptionFourPuzzle
import com.pklein.mariage.ui.atome.MatchstickPuzzleState
import com.pklein.mariage.ui.screen.MatchstickPuzzleScreen
import com.pklein.mariage.ui.theme.annotatedStringResource
import com.pklein.mariage.utils.uiUtils.Alerts
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReceptionFourFragment : Fragment() {
    private val gameStep = GameStep.Reception4
    private val universStep = SharedPreferenceKey.UNIVERS_2_RECEPTION
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
                val puzzle =
                    rememberSaveable(saver = MatchstickPuzzleState.saver(ReceptionFourPuzzle)) {
                        MatchstickPuzzleState(ReceptionFourPuzzle)
                    }

                MatchstickPuzzleScreen(
                    title = R.string.chambre_froide_title,
                    message = annotatedStringResource(id = R.string.reception_four_message),
                    state = puzzle,
                    onHomeClick = {
                        startActivity(Intent(requireContext(), CarnetBordActivity::class.java))
                    },
                    onValidateClick = ::onValidateClicked,
                )
            }
        }
    }

    private fun onValidateClicked(isSolved: Boolean) {
        context?.let {
            if (isSolved) {
                Alerts.showSuccess(it, gameProgress.playerName, gameProgress.gender, ::launchNext)
            } else {
                Alerts.showError(it) { gameProgress.updateError() }
            }
        }
    }

    private fun launchNext() {
        findNavController().navigate(R.id.action_reception_four_to_five)
    }
}
