package com.pklein.mariage.presentation.combatFinal

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
import com.pklein.mariage.data.model.Trophies
import com.pklein.mariage.data.repository.PlayerGender
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.carnetBord.CarnetBordActivity
import com.pklein.mariage.presentation.combatFinal.screen.CombatFinalOneScreen
import com.pklein.mariage.utils.uiUtils.Alerts
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CombatFinalOneFragment : Fragment() {
    private val gameProgress: GameProgressViewModel by activityViewModels()
    private val gameStep = GameStep.CombatFinal1
    private val universStep = SharedPreferenceKey.UNIVERS_3

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        gameProgress.storePage(gameStep)
        gameProgress.storeUniversPage(universStep, gameStep)

        return ComposeView(requireContext()).apply {
            setContent {
                CombatFinalOneScreen(
                    onHomeClick = {
                        startActivity(Intent(requireContext(), CarnetBordActivity::class.java))
                    },
                    onContinueClick = ::onContinueClicked,
                    onNoxTapped = ::onNoxTapped,
                )
            }
        }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        if (!gameProgress.isTrophyUnlocked(Trophies.TROPHY_COMBAT_FINAL)) {
            gameProgress.saveTrophy(Trophies.TROPHY_COMBAT_FINAL)
            Alerts.showTrophyUnlocked(
                requireContext(),
                Trophies.TROPHY_COMBAT_FINAL,
            )
        }
    }

    private fun onNoxTapped() {
        if (!gameProgress.isTrophyUnlocked(Trophies.TROPHY_NOX_FACE_OFF)) {
            gameProgress.saveTrophy(Trophies.TROPHY_NOX_FACE_OFF)
            context?.let {
                Alerts.showTrophyUnlocked(it, Trophies.TROPHY_NOX_FACE_OFF)
            }
        }
    }

    private fun onContinueClicked() {
        if (gameProgress.gender == PlayerGender.RABBIT) {
            findNavController().navigate(R.id.action_combat_one_to_two_rabbit)
        } else {
            findNavController().navigate(R.id.action_combat_one_to_two_dog)
        }
    }
}
