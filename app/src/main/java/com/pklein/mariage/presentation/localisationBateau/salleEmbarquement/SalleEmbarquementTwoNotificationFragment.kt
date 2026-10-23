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
import com.pklein.mariage.databinding.FragmentSalleEmbarquementTwoNotificationBinding
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.carnetBord.CarnetBordActivity
import com.pklein.mariage.utils.Notification
import com.pklein.mariage.utils.extension.addSystemWindowInsetToMargin
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SalleEmbarquementTwoNotificationFragment : Fragment() {
    private var binding: FragmentSalleEmbarquementTwoNotificationBinding? = null
    private val gameProgress: GameProgressViewModel by activityViewModels()
    private var counter: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View =
        FragmentSalleEmbarquementTwoNotificationBinding
            .inflate(inflater, container, false)
            .also { binding = it }
            .root

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val binding = binding ?: return
        gameProgress.storePage(GameStep.SalleEmbarquement2)
        gameProgress.storeUniversPage(
            SharedPreferenceKey.UNIVERS_1,
            GameStep.SalleEmbarquement2,
        )

        binding.ivHome.setOnClickListener {
            activity?.startActivity(
                Intent(
                    requireContext(),
                    CarnetBordActivity::class.java,
                ),
            )
        }
        binding.buttonEmbarquementTwoResend.setOnClickListener {
            counter += 1
            launchNotification()
            if (counter >= 3) {
                binding.tvEmbarquementTwoNotificationRetry3.visibility = View.VISIBLE
                binding.tvEmbarquementTwoNotificationRetry3.setOnClickListener {
                    findNavController().navigate(R.id.action_notification_to_three)
                }
            } else {
                binding.tvEmbarquementTwoNotificationRetry3.visibility = View.INVISIBLE
            }
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
