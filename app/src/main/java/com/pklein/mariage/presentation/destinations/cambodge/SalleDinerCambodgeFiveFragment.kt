package com.pklein.mariage.presentation.destinations.cambodge

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
import com.pklein.mariage.presentation.destinations.cambodge.screen.CambodgeFiveScreen
import com.pklein.mariage.utils.uiUtils.Alerts
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SalleDinerCambodgeFiveFragment : Fragment() {
    private val gameProgress: GameProgressViewModel by activityViewModels()
    private val gameStep = GameStep.SalleDinerCambodge5
    private val universStep = SharedPreferenceKey.UNIVERS_2_CAMBODGE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View =
        ComposeView(requireContext()).apply {
            setContent {
                CambodgeFiveScreen(
                    onHomeClick = {
                        startActivity(Intent(requireContext(), CarnetBordUniversActivity::class.java))
                    },
                    onContinueClick = ::onContinueClicked,
                )
            }
        }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        gameProgress.storePage(gameStep)
        if (!gameProgress.isTrophyUnlocked(Trophies.TROPHY_RABBIT_MANGUE)) {
            gameProgress.saveTrophy(Trophies.TROPHY_RABBIT_MANGUE)
            context?.let {
                Alerts.showTrophyUnlocked(it, Trophies.TROPHY_RABBIT_MANGUE)
            }
        }
        gameProgress.storeUniversPage(universStep, gameStep)
    }

    private fun onContinueClicked() {
        gameProgress.finishUnivers(universStep)
        activity?.startActivity(Intent(requireContext(), CarnetBordUniversActivity::class.java))
        activity?.finish()
    }
}
