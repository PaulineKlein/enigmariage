package com.pklein.mariage.presentation.sallePhotobooth

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

@AndroidEntryPoint
class SallePhotoboothTwoFragment : QuestionFragment() {
    override val gameStep = GameStep.SallePhotobooth2
    override val universStep = SharedPreferenceKey.UNIVERS_3

    override fun onSetupView(binding: FragmentQuestionBinding) {
        binding.tvQuestionTitre.text = getString(R.string.photobooth_two_titre2)
        binding.tvQuestionMessage.text = getString(R.string.photobooth_two_enigme)
        binding.animationQuestionLottie.visibility = View.GONE
        binding.animationQuestionImage.visibility = View.VISIBLE
    }

    override fun onValidateCLicked(response: String) {
        context?.let {
            val responseStep = gameProgress.getReponse(gameStep)?.let { getString(it) }
            if (responseStep != null && response.formatAnswer() == responseStep) {
                Alerts.showSuccess(it, gameProgress.playerName, gameProgress.gender, ::launchNext)
            } else {
                Alerts.showError(it) {
                    gameProgress.updateError()
                }
            }
        }
    }

    override fun launchNext() {
        findNavController().navigate(R.id.action_photobooth_two_to_three)
    }
}
