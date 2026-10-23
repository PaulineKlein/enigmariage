package com.pklein.mariage.presentation.destinations.cambodge

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

private const val RESPONSE = "karate"

@AndroidEntryPoint
class SalleDinerCambodgeTwoFragment : QuestionFragment() {
    override val gameStep = GameStep.SalleDinerCambodge2
    override val universStep = SharedPreferenceKey.UNIVERS_2_CAMBODGE

    override fun onSetupView(binding: FragmentQuestionBinding) {
        binding.ivHome.setOnClickListener {
            activity?.startActivity(
                Intent(
                    requireContext(),
                    CarnetBordUniversActivity::class.java
                )
            )
        }
        binding.tvQuestionTitre.text = getString(R.string.diner_titre_cambodge)
        binding.tvQuestionMessage.text = resources.getText(R.string.diner_cambodge_two_question)
        binding.animationQuestionLottie.visibility = View.GONE
        binding.animationQuestionImage.visibility = View.VISIBLE
        binding.animationQuestionImage.setImageResource(R.drawable.image_rebus_karate)
        binding.animationQuestionImage.contentDescription =
            getString(R.string.image_enigme_accessibility)
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
        findNavController().navigate(R.id.action_cambodge_two_to_three)
    }
}
