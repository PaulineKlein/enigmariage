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
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.carnetBord.CarnetBordActivity
import com.pklein.mariage.presentation.combatFinal.screen.CombatFinalThreeScreen
import com.pklein.mariage.utils.Notification
import com.pklein.mariage.utils.uiUtils.Alerts
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CombatFinalThreeFragment : Fragment() {
    private val gameProgress: GameProgressViewModel by activityViewModels()
    private val gameStep = GameStep.CombatFinal3
    private val universStep = SharedPreferenceKey.UNIVERS_3

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        gameProgress.storePage(gameStep)
        gameProgress.storeUniversPage(universStep, gameStep)
        (activity as? CombatFinalActivity)?.stopCountDown()

        return ComposeView(requireContext()).apply {
            setContent {
                CombatFinalThreeScreen(
                    playerName = gameProgress.playerName.orEmpty(),
                    onHomeClick = {
                        startActivity(Intent(requireContext(), CarnetBordActivity::class.java))
                    },
                    onResultsClick = {
                        findNavController().navigate(R.id.action_combat_three_to_results)
                    },
                )
            }
        }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        // Victory trophies : only award once per playthrough, even if the user
        // navigates back and forward and this view gets recreated in between.
        if (!gameProgress.victoryTrophyAwardedThisRun) {
            gameProgress.victoryTrophyAwardedThisRun = true
            if (!gameProgress.isTrophyUnlocked(Trophies.TROPHY_VICTOIRE_1)) {
                gameProgress.saveTrophy(Trophies.TROPHY_VICTOIRE_1)
                context?.let {
                    Alerts.showTrophyUnlocked(
                        it,
                        Trophies.TROPHY_VICTOIRE_1,
                    )
                }
            } else if (!gameProgress.isTrophyUnlocked(Trophies.TROPHY_VICTOIRE_2)) {
                gameProgress.saveTrophy(Trophies.TROPHY_VICTOIRE_2)
                context?.let {
                    Alerts.showTrophyUnlocked(
                        it,
                        Trophies.TROPHY_VICTOIRE_2,
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        launchNotification()
    }

    private fun launchNotification() {
        context?.let {
            Notification.cancelNotification(it)
            Notification.sendNotificationCombatFinal(it)
        }
    }
}
