package com.pklein.mariage.presentation.salleCocktails.salleDiner

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.pklein.mariage.R
import com.pklein.mariage.data.model.SharedPreferenceKey
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.carnetBord.CarnetBordActivity
import com.pklein.mariage.presentation.carnetBord.CarnetBordUniversActivity
import com.pklein.mariage.ui.screen.QuestionScreen
import com.pklein.mariage.ui.theme.annotatedStringResource
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts
import com.pklein.mariage.utils.uiUtils.PopinType
import dagger.hilt.android.AndroidEntryPoint

private const val RESPONSE = "canada"

@AndroidEntryPoint
class SalleDinerTwoFragment : Fragment() {
    private val gameStep = GameStep.SalleDiner2
    private val universStep = SharedPreferenceKey.UNIVERS_2_COCKTAILS
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
                    title = R.string.diner_one_titre,
                    message = annotatedStringResource(id = R.string.diner_two_message),
                    enigmeImage = R.drawable.image_jo_canada,
                    onHomeClick = {
                        startActivity(Intent(requireContext(), CarnetBordActivity::class.java))
                    },
                    onValidateClick = ::onValidateClicked,
                )
            }
        }
    }

    private fun onValidateClicked(response: String) {
        context?.let {
            if (response.formatAnswer().contains(RESPONSE)) {
                Alerts.showPopup(
                    it,
                    getString(R.string.diner_two_bravo, gameProgress.playerName),
                    ::launchNext,
                    PopinType.CLAPPING,
                )
            } else {
                Alerts.showError(it) {
                    gameProgress.updateError()
                }
            }
        }
    }

    private fun launchNext() {
        gameProgress.finishUnivers(universStep)
        activity?.startActivity(Intent(requireContext(), CarnetBordUniversActivity::class.java))
        activity?.finish()
    }
}
