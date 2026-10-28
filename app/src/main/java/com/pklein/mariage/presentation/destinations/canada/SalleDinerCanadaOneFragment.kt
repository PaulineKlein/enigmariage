package com.pklein.mariage.presentation.destinations.canada

import android.animation.ValueAnimator
import android.content.Intent
import android.view.View
import androidx.navigation.fragment.findNavController
import com.pklein.mariage.R
import com.pklein.mariage.data.model.SharedPreferenceKey
import com.pklein.mariage.databinding.FragmentQuestionBinding
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.QuestionFragment
import com.pklein.mariage.presentation.carnetBord.CarnetBordUniversActivity
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts
import dagger.hilt.android.AndroidEntryPoint

private const val RESPONSE = "erable"

@AndroidEntryPoint
class SalleDinerCanadaOneFragment : QuestionFragment() {
    override val gameStep = GameStep.SalleDinerCanada1
    override val universStep = SharedPreferenceKey.UNIVERS_2_CANADA

    override fun onSetupView(binding: FragmentQuestionBinding) {
        binding.ivHome.setOnClickListener {
            activity?.startActivity(
                Intent(
                    requireContext(),
                    CarnetBordUniversActivity::class.java,
                ),
            )
        }
        binding.animationQuestionLottie.apply {
            visibility = View.VISIBLE
            setAnimation("animation_leaves.json")
            playAnimation()
            repeatCount = ValueAnimator.INFINITE
        }
        binding.tvQuestionTitre.text = getString(R.string.diner_titre_canada)
        binding.tvQuestionMessage.text = resources.getText(R.string.diner_canada_one_message)
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
        findNavController().navigate(R.id.action_canada_one_to_two)
    }
}
