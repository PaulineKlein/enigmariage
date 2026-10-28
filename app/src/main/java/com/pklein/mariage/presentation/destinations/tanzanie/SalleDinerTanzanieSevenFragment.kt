package com.pklein.mariage.presentation.destinations.tanzanie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.pklein.mariage.data.model.SharedPreferenceKey
import com.pklein.mariage.data.model.Trophies
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.carnetBord.CarnetBordUniversActivity
import com.pklein.mariage.presentation.destinations.tanzanie.screen.TanzanieSevenScreen
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts
import dagger.hilt.android.AndroidEntryPoint

private const val RESPONSE = "destination italie"

@AndroidEntryPoint
class SalleDinerTanzanieSevenFragment : Fragment() {
    private val gameStep = GameStep.SalleDinerTanzanie7
    private val universStep = SharedPreferenceKey.UNIVERS_2_TANZANIE
    private val gameProgress: GameProgressViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        gameProgress.storePage(gameStep)
        gameProgress.storeUniversPage(universStep, gameStep)
        if (!gameProgress.isTrophyUnlocked(Trophies.TROPHY_DOG_CACAO)) {
            gameProgress.saveTrophy(Trophies.TROPHY_DOG_CACAO)
            context?.let {
                Alerts.showTrophyUnlocked(it, getString(Trophies.TROPHY_DOG_CACAO.message))
            }
        }

        return ComposeView(requireContext()).apply {
            setContent {
                TanzanieSevenScreen(
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
        gameProgress.finishUnivers(SharedPreferenceKey.UNIVERS_2_TANZANIE)
        activity?.startActivity(Intent(requireContext(), CarnetBordUniversActivity::class.java))
        activity?.finish()
    }
}
