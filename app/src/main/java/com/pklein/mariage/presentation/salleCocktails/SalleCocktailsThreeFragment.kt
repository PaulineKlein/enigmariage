package com.pklein.mariage.presentation.salleCocktails

import android.animation.ValueAnimator
import android.view.View
import androidx.navigation.fragment.findNavController
import com.pklein.mariage.R
import com.pklein.mariage.data.model.SharedPreferenceKey
import com.pklein.mariage.databinding.FragmentQuestionBinding
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.QuestionFragment
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts
import dagger.hilt.android.AndroidEntryPoint

private const val RESPONSE = "cactus"

@AndroidEntryPoint
class SalleCocktailsThreeFragment : QuestionFragment() {
    override val gameStep = GameStep.SalleCocktail3
    override val universStep = SharedPreferenceKey.UNIVERS_2_COCKTAILS

    override fun onSetupView(binding: FragmentQuestionBinding) {
        binding.animationQuestionLottie.apply {
            visibility = View.VISIBLE
            setAnimation("animation_green.json")
            playAnimation()
            repeatCount = ValueAnimator.INFINITE
        }
        binding.tvQuestionTitre.text = getString(R.string.cocktail_one_titre)
        binding.tvQuestionMessage.text = resources.getText(R.string.cocktail_three_message)
    }

    override fun onValidateCLicked(response: String) {
        context?.let {
            if (response.formatAnswer() == RESPONSE) {
                Alerts.showSuccess(it, gameProgress.playerName, gameProgress.gender, ::launchNext)
            } else {
                Alerts.showError(it) {
                    gameProgress.updateError()
                }
            }
        }
    }

    override fun launchNext() {
        findNavController().navigate(R.id.action_three_to_four)
    }
}
