package com.pklein.mariage.presentation.destinations.cambodge

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
import com.pklein.mariage.databinding.FragmentSalleDinerCambodgeThreeBinding
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.carnetBord.CarnetBordUniversActivity
import com.pklein.mariage.utils.extension.addSystemWindowInsetToMargin
import com.pklein.mariage.utils.uiUtils.Alerts
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SalleDinerCambodgeThreeFragment : Fragment() {
    private val gameProgress: GameProgressViewModel by activityViewModels()
    private val gameStep = GameStep.SalleDinerCambodge3
    private val universStep = SharedPreferenceKey.UNIVERS_2_CAMBODGE
    private var binding: FragmentSalleDinerCambodgeThreeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View =
        FragmentSalleDinerCambodgeThreeBinding
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
                    CarnetBordUniversActivity::class.java,
                ),
            )
        }
        binding.buttonDinerCambodgeThree.setOnClickListener {
            val kanjiId = binding.radioGroupKanji.checkedRadioButtonId
            context?.let {
                if (kanjiId == R.id.radioReponse1) {
                    Alerts.showSuccess(
                        it,
                        gameProgress.playerName,
                        gameProgress.gender,
                        ::launchNext,
                    )
                } else {
                    Alerts.showError(it) {
                        gameProgress.updateError()
                    }
                }
            }
        }
        binding.root.addSystemWindowInsetToMargin()
    }

    private fun launchNext() {
        findNavController().navigate(R.id.action_cambodge_three_to_four)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
