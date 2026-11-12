package com.pklein.mariage.presentation.salleCocktails

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
import com.pklein.mariage.presentation.carnetBord.CarnetBordActivity
import com.pklein.mariage.presentation.salleCocktails.screen.CocktailsFourScreen
import com.pklein.mariage.presentation.salleCocktails.screen.ZONE_DESERT
import com.pklein.mariage.utils.uiUtils.Alerts
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SalleCocktailsFourFragment : Fragment() {
    private val gameProgress: GameProgressViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        gameProgress.storePage(GameStep.SalleCocktail4)
        gameProgress.storeUniversPage(
            SharedPreferenceKey.UNIVERS_2_COCKTAILS,
            GameStep.SalleCocktail4,
        )

        return ComposeView(requireContext()).apply {
            setContent {
                CocktailsFourScreen(
                    playerName = gameProgress.playerName ?: "",
                    onHomeClick = {
                        startActivity(
                            Intent(
                                requireContext(),
                                CarnetBordActivity::class.java,
                            ),
                        )
                    },
                    onValidateClick = ::onValidateClicked,
                )
            }
        }
    }

    private fun onValidateClicked(zone: Int) {
        context?.let {
            if (zone == ZONE_DESERT) {
                Alerts.showSuccess(it, gameProgress.playerName, gameProgress.gender, ::launchNext)
            } else {
                Alerts.showError(it) { gameProgress.updateError() }
            }
        }
    }

    private fun launchNext() {
        findNavController().navigate(R.id.action_four_to_five)
    }
}
