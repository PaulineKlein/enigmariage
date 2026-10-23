package com.pklein.mariage.presentation.localisationBateau.salleEmbarquement

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
                Alerts.showTrophyUnlocked(it, getString(Trophies.TROPHY_EMBARQUEMENT.message))
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
            findNavController().navigate(R.id.action_two_to_notification)
            launchNotification()
        }
        binding.root.addSystemWindowInsetToMargin()
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
