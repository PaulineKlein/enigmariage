package com.pklein.mariage.presentation.localisationBateau.salleEmbarquement

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
import com.pklein.mariage.presentation.localisationBateau.salleEmbarquement.screen.EmbarquementTwoNotificationScreen
import com.pklein.mariage.utils.Notification
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SalleEmbarquementTwoNotificationFragment : Fragment() {
    private val gameProgress: GameProgressViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        gameProgress.storePage(GameStep.SalleEmbarquement2)
        gameProgress.storeUniversPage(
            SharedPreferenceKey.UNIVERS_1,
            GameStep.SalleEmbarquement2,
        )

        return ComposeView(requireContext()).apply {
            setContent {
                EmbarquementTwoNotificationScreen(
                    onHomeClick = {
                        startActivity(Intent(requireContext(), CarnetBordActivity::class.java))
                    },
                    onResendClick = ::launchNotification,
                    onHelpClick = { findNavController().navigate(R.id.action_notification_to_three) },
                )
            }
        }
    }

    private fun launchNotification() {
        context?.let {
            Notification.cancelNotification(it)
            Notification.sendNotificationSalleEmbarquement(it)
        }
    }
}
