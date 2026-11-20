package com.pklein.mariage.presentation.sallePhotobooth

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
import com.pklein.mariage.presentation.carnetBord.CarnetBordActivity
import com.pklein.mariage.ui.screen.QuestionScreen
import com.pklein.mariage.ui.theme.annotatedStringResource
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SallePhotoboothTwoFragment : Fragment() {
    private val gameStep = GameStep.SallePhotobooth2
    private val universStep = SharedPreferenceKey.UNIVERS_3
    private val gameProgress: GameProgressViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        gameProgress.storePage(gameStep)
        gameProgress.storeUniversPage(universStep, gameStep)

        val enigme = gameProgress.getEnigme(gameStep)
        val enigmeAccessibility = gameProgress.getEnigmeAccessibility(gameStep)

        if (enigme == null || enigmeAccessibility == null) {
            throw Exception("Informations cannot be null")
        }

        return ComposeView(requireContext()).apply {
            setContent {
                QuestionScreen(
                    title = R.string.photobooth_two_titre2,
                    message = annotatedStringResource(id = R.string.photobooth_two_enigme),
                    enigmeImage = enigme,
                    enigmeImageDescription = stringResource(enigmeAccessibility),
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
            val responseStep = gameProgress.getReponse(gameStep)?.let { getString(it) }
            if (responseStep != null && response.formatAnswer() == responseStep) {
                Alerts.showSuccess(it, gameProgress.playerName, gameProgress.gender, ::launchNext)
            } else {
                Alerts.showError(it) {
                    gameProgress.updateError()
                }
            }
        }
    }

    private fun launchNext() {
        findNavController().navigate(R.id.action_photobooth_two_to_three)
    }
}
