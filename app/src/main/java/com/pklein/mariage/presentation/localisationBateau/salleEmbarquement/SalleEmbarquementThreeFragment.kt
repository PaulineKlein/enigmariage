package com.pklein.mariage.presentation.localisationBateau.salleEmbarquement

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
import com.pklein.mariage.databinding.FragmentSalleEmbarquementThreeBinding
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.carnetBord.CarnetBordActivity
import com.pklein.mariage.utils.extension.addSystemWindowInsetToMargin
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcher
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcherListener
import com.pklein.mariage.utils.uiUtils.PopinType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SalleEmbarquementThreeFragment :
    Fragment(),
    CheckEmptyTextWatcherListener {
    private val gameStep = GameStep.SalleEmbarquement3
    private val universStep = SharedPreferenceKey.UNIVERS_1
    private var binding: FragmentSalleEmbarquementThreeBinding? = null
    private val gameProgress: GameProgressViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View =
        FragmentSalleEmbarquementThreeBinding
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

        val description = gameProgress.getDescription(gameStep)
        val enigme = gameProgress.getEnigme(gameStep)
        val clue = gameProgress.getClue(gameStep)

        if (description == null || enigme == null || clue == null) {
            throw Exception("Informations cannot be null")
        }

        binding.tvEnigme.text = getString(enigme)
        binding.tvDescription.text = getString(description)

        binding.ivHome.setOnClickListener {
            activity?.startActivity(
                Intent(
                    requireContext(),
                    CarnetBordActivity::class.java,
                ),
            )
        }
        binding.etEmbarquementThreeAnswer.addTextChangedListener(CheckEmptyTextWatcher(this))
        binding.buttonEmbarquementThree.setOnClickListener {
            onValidateCLicked(binding.etEmbarquementThreeAnswer.text.toString())
        }
        binding.layoutIndice.root.setOnClickListener {
            context?.let {
                Alerts.showClue(it, getString(clue)) {
                    updateClue()
                }
            }
        }
        binding.root.addSystemWindowInsetToMargin()
    }

    private fun updateClue() {
        gameProgress.updateClue()
        if (!gameProgress.isTrophyUnlocked(Trophies.TROPHY_CLUE_1)) {
            gameProgress.saveTrophy(Trophies.TROPHY_CLUE_1)
            context?.let {
                Alerts.showTrophyUnlocked(
                    it,
                    getString(Trophies.TROPHY_CLUE_1.message),
                )
            }
        }
    }

    private fun onValidateCLicked(response: String) {
        context?.let { ctx ->
            val responseStep = gameProgress.getReponse(gameStep)?.let { getString(it) }
            if (responseStep != null && response.formatAnswer() == responseStep) {
                Alerts.showPopup(
                    ctx,
                    getString(R.string.embarquement_three_reponse_bravo, gameProgress.playerName),
                    ::launchNext,
                    PopinType.CLAPPING,
                )
            } else {
                Alerts.showError(ctx) {
                    gameProgress.updateError()
                }
            }
        }
    }

    override fun onTextEmpty() {
        binding?.buttonEmbarquementThree?.isEnabled = false
    }

    override fun onTextNotEmpty() {
        binding?.buttonEmbarquementThree?.isEnabled = true
    }

    private fun launchNext() {
        gameProgress.finishUnivers(universStep)
        activity?.startActivity(Intent(requireContext(), CarnetBordActivity::class.java))
        activity?.finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
