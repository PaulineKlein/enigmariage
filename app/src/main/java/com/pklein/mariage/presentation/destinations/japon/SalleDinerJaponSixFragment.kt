package com.pklein.mariage.presentation.destinations.japon

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
import com.pklein.mariage.data.model.Trophies
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.carnetBord.CarnetBordUniversActivity
import com.pklein.mariage.presentation.destinations.japon.screen.JaponSixScreen
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts
import com.pklein.mariage.utils.uiUtils.PopinType
import dagger.hilt.android.AndroidEntryPoint

private const val RESPONSE_1 = "rendez vous au cambodge"
private const val RESPONSE_2 = "rendez-vous au cambodge"

@AndroidEntryPoint
class SalleDinerJaponSixFragment : Fragment() {
    private val gameProgress: GameProgressViewModel by activityViewModels()
    private val gameStep = GameStep.SalleDinerJapon6
    private val universStep = SharedPreferenceKey.UNIVERS_2_JAPON

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        gameProgress.storePage(gameStep)
        gameProgress.storeUniversPage(universStep, gameStep)

        return ComposeView(requireContext()).apply {
            setContent {
                JaponSixScreen(
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

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        if (!gameProgress.isTrophyUnlocked(Trophies.TROPHY_RABBIT_TAPIOCA)) {
            gameProgress.saveTrophy(Trophies.TROPHY_RABBIT_TAPIOCA)
            Alerts.showTrophyUnlocked(
                requireContext(),
                Trophies.TROPHY_RABBIT_TAPIOCA,
            )
        }
    }

    private fun onValidateClicked(response: String) {
        context?.let {
            val answer = response.formatAnswer()
            if (answer == RESPONSE_1 || answer == RESPONSE_2) {
                Alerts.showPopup(
                    it,
                    getString(R.string.diner_japon_six_bravo, gameProgress.playerName),
                    ::launchNext,
                    PopinType.CLAPPING,
                )
            } else {
                Alerts.showError(it) { gameProgress.updateError() }
            }
        }
    }

    private fun launchNext() {
        gameProgress.finishUnivers(universStep)
        activity?.startActivity(Intent(requireContext(), CarnetBordUniversActivity::class.java))
        activity?.finish()
    }
}
