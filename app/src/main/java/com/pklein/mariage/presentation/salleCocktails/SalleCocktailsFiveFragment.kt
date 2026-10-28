package com.pklein.mariage.presentation.salleCocktails

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

private const val RESPONSE = "gourde"

@AndroidEntryPoint
class SalleCocktailsFiveFragment : QuestionFragment() {
    override val gameStep = GameStep.SalleCocktail5
    override val universStep = SharedPreferenceKey.UNIVERS_2_COCKTAILS

    override fun onSetupView(binding: FragmentQuestionBinding) {
        binding.tvQuestionTitre.text = getString(R.string.cocktail_one_titre)
        binding.tvQuestionMessage.text = resources.getText(R.string.cocktail_five_message)
        binding.animationQuestionLottie.visibility = View.GONE
        binding.animationQuestionImage.visibility = View.VISIBLE
        binding.animationQuestionImage.setImageResource(R.drawable.image_village_desert)
    }

    override fun onValidateCLicked(response: String) {
        context?.let {
            if (response.formatAnswer().contains(RESPONSE)) {
                Alerts.showSuccess(it, gameProgress.playerName, gameProgress.gender, ::launchNext)
            } else {
                Alerts.showError(it) {
                    gameProgress.updateError()
                }
            }
        }
    }

    override fun launchNext() {
        findNavController().navigate(R.id.action_five_to_six)
    }
}
