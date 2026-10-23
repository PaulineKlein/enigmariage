package com.pklein.mariage.presentation.combatFinal

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
import com.pklein.mariage.ui.atome.LogigrammeState
import com.pklein.mariage.ui.screen.LogigrammeScreen
import com.pklein.mariage.ui.theme.annotatedStringResource
import com.pklein.mariage.utils.uiUtils.Alerts
import com.pklein.mariage.utils.uiUtils.PopinType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CombatFinalTwoDogFragment : Fragment() {
    private val gameStep = GameStep.CombatFinal2Dog
    private val universStep = SharedPreferenceKey.UNIVERS_3
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
                val state =
                    rememberSaveable(saver = LogigrammeState.saver(CombatFinalDogPuzzle.solution)) {
                        LogigrammeState(CombatFinalDogPuzzle.solution)
                    }
                LogigrammeScreen(
                    title = R.string.combatFinal_titre,
                    clue = CombatFinalDogPuzzle.clue,
                    question = annotatedStringResource(id = R.string.combatFinal_dog_question),
                    help = annotatedStringResource(id = R.string.combatFinal_dog_help),
                    rowLabels = CombatFinalDogPuzzle.rowLabels,
                    columnLabels = CombatFinalDogPuzzle.columnLabels,
                    words = CombatFinalDogPuzzle.words,
                    state = state,
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
                Alerts.showPopup(
                    it,
                    getString(R.string.combatFinal_sucess),
                    ::launchNext,
                    PopinType.FINAL_SUCCESS,
                )
            } else {
                Alerts.showError(it) { gameProgress.updateError() }
            }
        }
    }

    private fun launchNext() {
        gameProgress.storeEndTime()
        findNavController().navigate(R.id.action_combat_two_dog_to_three)
    }
}
