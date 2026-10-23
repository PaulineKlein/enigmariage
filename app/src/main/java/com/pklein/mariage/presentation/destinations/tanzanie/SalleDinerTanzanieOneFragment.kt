package com.pklein.mariage.presentation.destinations.tanzanie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.pklein.mariage.R
import com.pklein.mariage.data.model.SharedPreferenceKey
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.carnetBord.CarnetBordUniversActivity
import com.pklein.mariage.presentation.destinations.tanzanie.screen.TanzanieOneScreen
import com.pklein.mariage.utils.uiUtils.Alerts
import dagger.hilt.android.AndroidEntryPoint

private const val RESPONSE = 1

@AndroidEntryPoint
class SalleDinerTanzanieOneFragment : Fragment() {
    private val gameStep = GameStep.SalleDinerTanzanie1
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
                TanzanieOneScreen(
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

    private fun onValidateClicked(selected: Int) {
        context?.let {
            if (selected == RESPONSE) {
                Alerts.showSuccess(it, gameProgress.playerName, gameProgress.gender, ::launchNext)
            } else {
                Alerts.showError(it) { gameProgress.updateError() }
            }
        }
    }

    private fun launchNext() {
        findNavController().navigate(R.id.action_tanzanie_one_to_two)
    }
}
