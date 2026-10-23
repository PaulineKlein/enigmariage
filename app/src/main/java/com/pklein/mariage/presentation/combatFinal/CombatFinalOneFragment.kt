package com.pklein.mariage.presentation.combatFinal

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
import com.pklein.mariage.data.repository.PlayerGender
import com.pklein.mariage.databinding.FragmentCombatFinalOneBinding
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.carnetBord.CarnetBordActivity
import com.pklein.mariage.utils.extension.addSystemWindowInsetToMargin
import com.pklein.mariage.utils.uiUtils.Alerts
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CombatFinalOneFragment : Fragment() {
    private val gameProgress: GameProgressViewModel by activityViewModels()
    private val gameStep = GameStep.CombatFinal1
    private val universStep = SharedPreferenceKey.UNIVERS_3
    private var binding: FragmentCombatFinalOneBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View =
        FragmentCombatFinalOneBinding
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
        if (!gameProgress.isTrophyUnlocked(Trophies.TROPHY_COMBAT_FINAL)) {
            gameProgress.saveTrophy(Trophies.TROPHY_COMBAT_FINAL)
            context?.let {
                Alerts.showTrophyUnlocked(it, getString(R.string.trophy_progression_message_7))
            }
        }
        gameProgress.storeUniversPage(universStep, gameStep)

        binding.ivHome.setOnClickListener {
            activity?.startActivity(
                Intent(
                    requireContext(),
                    CarnetBordActivity::class.java,
                ),
            )
        }
        binding.buttonCombatFinalOne.setOnClickListener {
            if (gameProgress.gender == PlayerGender.RABBIT) {
                findNavController().navigate(R.id.action_combat_one_to_two_rabbit)
            } else {
                findNavController().navigate(R.id.action_combat_one_to_two_dog)
            }
        }
        binding.root.addSystemWindowInsetToMargin()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
