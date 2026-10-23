package com.pklein.mariage.presentation.combatFinal

import android.animation.Animator
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
import com.pklein.mariage.databinding.FragmentCombatFinalThreeBinding
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.carnetBord.CarnetBordActivity
import com.pklein.mariage.utils.Notification
import com.pklein.mariage.utils.extension.addSystemWindowInsetToMargin
import com.pklein.mariage.utils.uiUtils.Alerts
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CombatFinalThreeFragment : Fragment() {
    private var binding: FragmentCombatFinalThreeBinding? = null
    private val gameProgress: GameProgressViewModel by activityViewModels()
    private val gameStep = GameStep.CombatFinal3
    private val universStep = SharedPreferenceKey.UNIVERS_3
    private var nbRepeat = 1

    private val animationListener =
        object : Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator) {
                val binding = binding ?: return
                if (nbRepeat < 2) {
                    binding.animationKoLottie.playAnimation()
                    nbRepeat += 1
                } else {
                    binding.animationKoLottie.visibility = View.GONE
                    binding.animationKoImage.visibility = View.VISIBLE
                    binding.buttonCombatFinalThree.isEnabled = true
                }
            }

            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}

            override fun onAnimationCancel(animation: Animator) {}
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View =
        FragmentCombatFinalThreeBinding
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

        binding.ivHome.setOnClickListener {
            activity?.startActivity(
                Intent(requireContext(), CarnetBordActivity::class.java),
            )
        }
        (activity as? CombatFinalActivity)?.stopCountDown()
        binding.animationKoLottie.addAnimatorListener(animationListener)
        binding.buttonCombatFinalThree.setOnClickListener {
            findNavController().navigate(R.id.action_combat_three_to_results)
        }
        // Victory trophies : only award once per playthrough, even if the user
        // navigates back and forward and this view gets recreated in between.
        if (!gameProgress.victoryTrophyAwardedThisRun) {
            gameProgress.victoryTrophyAwardedThisRun = true
            if (!gameProgress.isTrophyUnlocked(Trophies.TROPHY_VICTOIRE_1)) {
                gameProgress.saveTrophy(Trophies.TROPHY_VICTOIRE_1)
                context?.let {
                    Alerts.showTrophyUnlocked(
                        it,
                        getString(Trophies.TROPHY_VICTOIRE_1.message),
                    )
                }
            } else if (!gameProgress.isTrophyUnlocked(Trophies.TROPHY_VICTOIRE_2)) {
                gameProgress.saveTrophy(Trophies.TROPHY_VICTOIRE_2)
                context?.let {
                    Alerts.showTrophyUnlocked(
                        it,
                        getString(Trophies.TROPHY_VICTOIRE_2.message),
                    )
                }
            }
        }

        binding.root.addSystemWindowInsetToMargin()
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.animationKoLottie?.removeAnimatorListener(animationListener)
        binding = null
        nbRepeat = 1
    }
}
