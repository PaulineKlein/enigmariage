package com.pklein.mariage.presentation.destinations.canada

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.pklein.mariage.R
import com.pklein.mariage.data.model.SharedPreferenceKey
import com.pklein.mariage.data.model.Trophies
import com.pklein.mariage.databinding.FragmentSalleDinerCanadaSixBinding
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.carnetBord.CarnetBordUniversActivity
import com.pklein.mariage.utils.extension.addSystemWindowInsetToMargin
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcher
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcherListener
import com.pklein.mariage.utils.uiUtils.PopinType
import dagger.hilt.android.AndroidEntryPoint

private const val RESPONSE = "direction le japon"

@AndroidEntryPoint
class SalleDinerCanadaSixFragment :
    Fragment(),
    CheckEmptyTextWatcherListener {
    private val gameProgress: GameProgressViewModel by activityViewModels()
    private var binding: FragmentSalleDinerCanadaSixBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View =
        FragmentSalleDinerCanadaSixBinding
            .inflate(inflater, container, false)
            .also { binding = it }
            .root

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val binding = binding ?: return
        gameProgress.storePage(GameStep.SalleDinerCanada6)
        if (!gameProgress.isTrophyUnlocked(Trophies.TROPHY_RABBIT_SIROP_ERABLE)) {
            gameProgress.saveTrophy(Trophies.TROPHY_RABBIT_SIROP_ERABLE)
            context?.let {
                Alerts.showTrophyUnlocked(
                    it,
                    getString(Trophies.TROPHY_RABBIT_SIROP_ERABLE.message),
                )
            }
        }
        gameProgress.storeUniversPage(
            SharedPreferenceKey.UNIVERS_2_CANADA,
            GameStep.SalleDinerCanada6,
        )

        binding.ivHome.setOnClickListener {
            activity?.startActivity(
                Intent(
                    requireContext(),
                    CarnetBordUniversActivity::class.java,
                ),
            )
        }
        binding.etDinerCanadaSixAnswer.addTextChangedListener(CheckEmptyTextWatcher(this))
        binding.buttonDinerCanadaSix.setOnClickListener {
            onValidateClicked(binding.etDinerCanadaSixAnswer.text.toString())
        }
        binding.root.addSystemWindowInsetToMargin()
    }

    private fun onValidateClicked(response: String) {
        context?.let {
            if (response.formatAnswer() == RESPONSE) {
                Alerts.showPopup(
                    it,
                    getString(R.string.diner_canada_six_bravo),
                    ::launchNext,
                    PopinType.CLAPPING,
                )
            } else {
                Alerts.showError(it) {
                    gameProgress.updateError()
                }
            }
        }
    }

    override fun onTextEmpty() {
        binding?.buttonDinerCanadaSix?.isEnabled = false
    }

    override fun onTextNotEmpty() {
        binding?.buttonDinerCanadaSix?.isEnabled = true
    }

    private fun launchNext() {
        gameProgress.finishUnivers(SharedPreferenceKey.UNIVERS_2_CANADA)
        activity?.startActivity(Intent(requireContext(), CarnetBordUniversActivity::class.java))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
