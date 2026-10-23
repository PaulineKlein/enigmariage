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
import com.pklein.mariage.data.repository.PlayerGender
import com.pklein.mariage.databinding.FragmentCombatFinalTwoRabbitBinding
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.carnetBord.CarnetBordActivity
import com.pklein.mariage.utils.extension.addSystemWindowInsetToMargin
import com.pklein.mariage.utils.uiUtils.Alerts
import com.pklein.mariage.utils.uiUtils.PopinType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CombatFinalTwoRabbitFragment : Fragment() {
    private var binding: FragmentCombatFinalTwoRabbitBinding? = null
    private val gameProgress: GameProgressViewModel by activityViewModels()
    private val gameStep = GameStep.CombatFinal2Rabbit
    private val universStep = SharedPreferenceKey.UNIVERS_3

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View =
        FragmentCombatFinalTwoRabbitBinding
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
                Intent(
                    requireContext(),
                    CarnetBordActivity::class.java,
                ),
            )
        }
        setMazeImage(binding)
        binding.buttonCombatFinalReponse.setOnClickListener {
            val kanjiId = binding.radioGroupCoups.checkedRadioButtonId
            if (kanjiId == R.id.radioReponse2) {
                context?.let {
                    Alerts.showPopup(
                        it,
                        getString(R.string.combatFinal_sucess),
                        ::launchNext,
                        PopinType.FINAL_SUCCESS,
                    )
                }
            } else {
                context?.let {
                    Alerts.showError(it) {
                        gameProgress.updateError()
                    }
                }
            }
        }
        binding.root.addSystemWindowInsetToMargin()
    }

    private fun setMazeImage(binding: FragmentCombatFinalTwoRabbitBinding) {
        if (gameProgress.gender == PlayerGender.RABBIT) {
            binding.ivCombatFinalMaze.setImageResource(R.drawable.image_maze_final_perso_woman)
        } else {
            binding.ivCombatFinalMaze.setImageResource(R.drawable.image_maze_final_perso_man)
        }
    }

    private fun launchNext() {
        gameProgress.storeEndTime()
        findNavController().navigate(R.id.action_combat_two_rabbit_to_three)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
