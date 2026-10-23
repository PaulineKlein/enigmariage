package com.pklein.mariage.presentation.destinations.tanzanie

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
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.carnetBord.CarnetBordUniversActivity
import com.pklein.mariage.ui.screen.QuestionScreen
import com.pklein.mariage.ui.theme.annotatedStringResource
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts
import dagger.hilt.android.AndroidEntryPoint

private const val RESPONSE = "moulage"

@AndroidEntryPoint
class SalleDinerTanzanieSixFragment : Fragment() {
    private val gameStep = GameStep.SalleDinerTanzanie6
    private val universStep = SharedPreferenceKey.UNIVERS_2_TANZANIE
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
                    title = R.string.diner_titre_tanzanie,
                    message = annotatedStringResource(R.string.diner_tanzanie_six_message),
                    enigmeImage = R.drawable.image_cacao,
                    enigmeImageDescription = stringResource(R.string.image_enigme_accessibility),
                    enigmeImageZoomable = true,
                    onHomeClick = {
                        startActivity(
                            Intent(
                                requireContext(),
                                CarnetBordUniversActivity::class.java,
                            ),
                        )
                    },
                    onValidateClick = ::onValidateClicked,
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
        findNavController().navigate(R.id.action_tanzanie_six_to_seven)
    }
}
