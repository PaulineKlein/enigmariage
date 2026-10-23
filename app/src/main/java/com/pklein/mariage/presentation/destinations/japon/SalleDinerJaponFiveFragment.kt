package com.pklein.mariage.presentation.destinations.japon

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
import com.pklein.mariage.utils.extension.showImageWithStfalconViewer
import com.pklein.mariage.utils.uiUtils.Alerts
import dagger.hilt.android.AndroidEntryPoint

private const val RESPONSE = "tapioca"

@AndroidEntryPoint
class SalleDinerJaponFiveFragment : QuestionFragment() {
    override val gameStep = GameStep.SalleDinerJapon5
    override val universStep = SharedPreferenceKey.UNIVERS_2_JAPON

    override fun onSetupView(binding: FragmentQuestionBinding) {
        binding.ivHome.setOnClickListener {
            activity?.startActivity(
                Intent(
                    requireContext(),
                    CarnetBordUniversActivity::class.java
                )
            )
        }
        binding.tvQuestionTitre.text = getString(R.string.diner_titre_japon)
        binding.tvQuestionMessage.text = resources.getText(R.string.diner_japon_five_question)
        binding.animationQuestionLottie.visibility = View.GONE
        binding.animationQuestionImage.apply {
            visibility = View.VISIBLE
            setImageResource(R.drawable.image_rebus_tapioca)
            contentDescription = getString(R.string.image_enigme_accessibility)
            setOnClickListener {
                context?.let { ctx ->
                    showImageWithStfalconViewer(
                        ctx,
                        R.drawable.image_rebus_tapioca
                    )
                }
            }
        }
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
        findNavController().navigate(R.id.action_japon_five_to_six)
    }
}
