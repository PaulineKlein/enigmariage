package com.pklein.mariage.presentation.localisationBateau.salleEmbarquement

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.pklein.mariage.R
import com.pklein.mariage.data.model.SharedPreferenceKey
import com.pklein.mariage.data.model.Trophies
import com.pklein.mariage.databinding.FragmentSalleEmbarquementTwoBinding
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.carnetBord.CarnetBordActivity
import com.pklein.mariage.utils.Notification
import com.pklein.mariage.utils.extension.addSystemWindowInsetToMargin
import com.pklein.mariage.utils.uiUtils.Alerts
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SalleEmbarquementTwoFragment : Fragment() {
    private val gameStep = GameStep.SalleEmbarquement2
    private val universStep = SharedPreferenceKey.UNIVERS_1
    private var binding: FragmentSalleEmbarquementTwoBinding? = null
    private val gameProgress: GameProgressViewModel by activityViewModels()

    // Granted or denied, the story goes on: without permission the notification is simply not shown.
    private val requestNotificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { goToNotification() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View =
        FragmentSalleEmbarquementTwoBinding
            .inflate(inflater, container, false)
            .also { binding = it }
            .root

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val binding = binding ?: return
        gameProgress.storePage(gameStep)
        gameProgress.storeUniversPage(universStep, gameStep)

        if (!gameProgress.isTrophyUnlocked(Trophies.TROPHY_EMBARQUEMENT)) {
            gameProgress.saveTrophy(Trophies.TROPHY_EMBARQUEMENT)
            context?.let {
                Alerts.showTrophyUnlocked(it, Trophies.TROPHY_EMBARQUEMENT)
            }
        }
        gameProgress.getDescription(gameStep)?.let {
            binding.tvEmbarquementTwoMessage.text = getText(it)
        }

        binding.ivHome.setOnClickListener {
            activity?.startActivity(
                Intent(
                    requireContext(),
                    CarnetBordActivity::class.java,
                ),
            )
        }
        binding.buttonEmbarquementTwo.setOnClickListener {
            requestNotificationPermissionIfNeeded()
        }
        binding.root.addSystemWindowInsetToMargin()
    }

    private fun requestNotificationPermissionIfNeeded() {
        val needsPermission =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS,
                ) != PackageManager.PERMISSION_GRANTED
        if (needsPermission) {
            requestNotificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            goToNotification()
        }
    }

    private fun goToNotification() {
        findNavController().navigate(R.id.action_two_to_notification)
        launchNotification()
    }

    private fun launchNotification() {
        context?.let {
            Notification.cancelNotification(it)
            Notification.sendNotificationSalleEmbarquement(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
