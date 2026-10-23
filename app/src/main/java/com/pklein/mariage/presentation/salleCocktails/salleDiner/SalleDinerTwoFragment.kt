package com.pklein.mariage.presentation.salleCocktails.salleDiner

import android.content.Intent
import android.view.View
import com.pklein.mariage.R
import com.pklein.mariage.data.model.SharedPreferenceKey
import com.pklein.mariage.databinding.FragmentQuestionBinding
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.QuestionFragment
import com.pklein.mariage.presentation.carnetBord.CarnetBordUniversActivity
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts
import com.pklein.mariage.utils.uiUtils.PopinType
import dagger.hilt.android.AndroidEntryPoint

private const val RESPONSE = "canada"

@AndroidEntryPoint
class SalleDinerTwoFragment : QuestionFragment() {
    override val gameStep = GameStep.SalleDiner2
    override val universStep = SharedPreferenceKey.UNIVERS_2_COCKTAILS

    override fun onSetupView(binding: FragmentQuestionBinding) {
        binding.tvQuestionTitre.text = getString(R.string.diner_one_titre)
        binding.tvQuestionMessage.text = resources.getText(R.string.diner_two_message)
        binding.animationQuestionLottie.visibility = View.GONE
        binding.animationQuestionImage.visibility = View.VISIBLE
        binding.animationQuestionImage.setImageResource(R.drawable.image_jo_canada)
    }

    override fun onValidateCLicked(response: String) {
        context?.let {
            if (response.formatAnswer().contains(RESPONSE)) {
                Alerts.showPopup(
                    it,
                    getString(R.string.diner_two_bravo),
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

    override fun launchNext() {
        gameProgress.finishUnivers(SharedPreferenceKey.UNIVERS_2_COCKTAILS)
        activity?.startActivity(Intent(requireContext(), CarnetBordUniversActivity::class.java))
    }
}
