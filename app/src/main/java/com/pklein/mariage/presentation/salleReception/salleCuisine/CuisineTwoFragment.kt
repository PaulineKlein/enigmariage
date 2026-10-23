package com.pklein.mariage.presentation.salleReception.salleCuisine

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.pklein.mariage.data.model.SharedPreferenceKey
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.carnetBord.CarnetBordActivity
import com.pklein.mariage.presentation.carnetBord.CarnetBordUniversActivity
import com.pklein.mariage.presentation.salleReception.salleCuisine.screen.CuisineTwoScreen
import com.pklein.mariage.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CuisineTwoFragment : Fragment() {
    private val gameStep = GameStep.Reception8
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
                AppTheme {
                    CuisineTwoScreen(
                        onHomeClick = ::onHomeClick,
                        onContinueClick = ::launchNext,
                    )
                }
            }
        }
    }

    private fun onHomeClick() {
        startActivity(Intent(requireContext(), CarnetBordActivity::class.java))
    }

    private fun launchNext() {
        gameProgress.finishUnivers(SharedPreferenceKey.UNIVERS_2_RECEPTION)
        activity?.startActivity(Intent(requireContext(), CarnetBordUniversActivity::class.java))
    }
}
