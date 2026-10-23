package com.pklein.mariage.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.pklein.mariage.data.model.SharedPreferenceKey
import com.pklein.mariage.data.model.Trophies
import com.pklein.mariage.databinding.FragmentQuestionBinding
import com.pklein.mariage.presentation.carnetBord.CarnetBordActivity
import com.pklein.mariage.utils.extension.addSystemWindowInsetToMargin
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.extension.hideKeyboard
import com.pklein.mariage.utils.uiUtils.Alerts
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcher
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcherListener

abstract class QuestionFragment :
    Fragment(),
    CheckEmptyTextWatcherListener {
    protected var binding: FragmentQuestionBinding? = null
    protected val gameProgress: GameProgressViewModel by activityViewModels()

    abstract val gameStep: GameStep
    abstract val universStep: SharedPreferenceKey

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View =
        FragmentQuestionBinding
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

        // setup Enigma :
        val description = gameProgress.getDescription(gameStep)
        val enigme = gameProgress.getEnigme(gameStep)
        val enigmeAccessibility = gameProgress.getEnigmeAccessibility(gameStep)
        val clue = gameProgress.getClue(gameStep)

        description?.let {
            binding.tvQuestionMessage.text = resources.getText(it)
        }
        enigme?.let { binding.animationQuestionImage.setImageResource(it) }
        enigmeAccessibility?.let {
            binding.animationQuestionImage.contentDescription =
                getString(it)
        }
        clue?.let { clue ->
            binding.layoutIndice.root.setOnClickListener {
                context?.let {
                    Alerts.showClue(it, getString(clue)) {
                        updateClue()
                    }
                }
            }
        }

        // Setup Listeners :
        binding.etQuestionAnswer.addTextChangedListener(CheckEmptyTextWatcher(this))
        binding.buttonQuestion.setOnClickListener {
            it.hideKeyboard()
            onValidateCLicked(binding.etQuestionAnswer.text.toString())
        }
        binding.ivHome.setOnClickListener {
            activity?.startActivity(
                Intent(
                    requireContext(),
                    CarnetBordActivity::class.java,
                ),
            )
        }

        binding.root.addSystemWindowInsetToMargin()
        onSetupView(binding)
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

    abstract fun onSetupView(binding: FragmentQuestionBinding)

    open fun onValidateCLicked(response: String) {
        context?.let { ctx ->
            val responseStep = gameProgress.getReponse(gameStep)?.let { getString(it) }

            if (responseStep != null && response.formatAnswer().contains(responseStep)) {
                Alerts.showSuccess(
                    ctx,
                    gameProgress.playerName,
                    gameProgress.gender,
                    ::launchNext,
                )
            } else {
                Alerts.showError(ctx) {
                    gameProgress.updateError()
                }
            }
        }
    }

    abstract fun launchNext()

    override fun onTextEmpty() {
        binding?.buttonQuestion?.isEnabled = false
    }

    override fun onTextNotEmpty() {
        binding?.buttonQuestion?.isEnabled = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
