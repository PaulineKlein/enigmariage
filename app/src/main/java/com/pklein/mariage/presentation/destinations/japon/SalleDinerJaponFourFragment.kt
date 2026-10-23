package com.pklein.mariage.presentation.destinations.japon

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
import com.pklein.mariage.databinding.FragmentSalleDinerJaponFourBinding
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.carnetBord.CarnetBordUniversActivity
import com.pklein.mariage.utils.extension.addSystemWindowInsetToMargin
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcher
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcherListener
import dagger.hilt.android.AndroidEntryPoint

private const val RESPONSE_1 = "bubble tea"
private const val RESPONSE_2 = "bubbletea"

@AndroidEntryPoint
class SalleDinerJaponFourFragment : Fragment(), CheckEmptyTextWatcherListener {
    private val gameProgress: GameProgressViewModel by activityViewModels()
    private var binding: FragmentSalleDinerJaponFourBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentSalleDinerJaponFourBinding.inflate(inflater, container, false)
            .also { binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = binding ?: return
        gameProgress.storePage(GameStep.SalleDinerJapon4)
        gameProgress.storeUniversPage(
            SharedPreferenceKey.UNIVERS_2_JAPON,
            GameStep.SalleDinerJapon4
        )

        binding.ivHome.setOnClickListener {
            activity?.startActivity(
                Intent(
                    requireContext(),
                    CarnetBordUniversActivity::class.java
                )
            )
        }
        binding.etDinerJaponFourAnswer.addTextChangedListener(CheckEmptyTextWatcher(this))
        binding.buttonDinerJaponFour.setOnClickListener {
            onValidateClicked(binding.etDinerJaponFourAnswer.text.toString())
        }
        binding.root.addSystemWindowInsetToMargin()
    }

    private fun onValidateClicked(response: String) {
        context?.let {
            if (response.formatAnswer() == RESPONSE_1 || response.formatAnswer() == RESPONSE_2) {
                Alerts.showSuccess(it, gameProgress.playerName, gameProgress.gender, ::launchNext)
            } else {
                Alerts.showError(it) {
                    gameProgress.updateError()
                }
            }
        }
    }

    override fun onTextEmpty() {
        binding?.buttonDinerJaponFour?.isEnabled = false
    }

    override fun onTextNotEmpty() {
        binding?.buttonDinerJaponFour?.isEnabled = true
    }

    private fun launchNext() {
        findNavController().navigate(R.id.action_japon_four_to_five)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
