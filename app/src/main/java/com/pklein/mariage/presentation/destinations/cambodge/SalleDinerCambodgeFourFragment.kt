package com.pklein.mariage.presentation.destinations.cambodge

import android.content.Intent
import android.view.View
import androidx.navigation.fragment.findNavController
import com.pklein.mariage.R
import com.pklein.mariage.data.model.SharedPreferenceKey
import com.pklein.mariage.data.model.Trophies
import com.pklein.mariage.databinding.FragmentQuestionBinding
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.QuestionFragment
import com.pklein.mariage.presentation.carnetBord.CarnetBordUniversActivity
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts
import dagger.hilt.android.AndroidEntryPoint

private const val RESPONSE = "mangue"

@AndroidEntryPoint
class SalleDinerCambodgeFourFragment : QuestionFragment() {
    override val gameStep = GameStep.SalleDinerCambodge4
    override val universStep = SharedPreferenceKey.UNIVERS_2_CAMBODGE

    override fun onSetupView(binding: FragmentQuestionBinding) {
        binding.ivHome.setOnClickListener {
            requireActivity().startActivity(
                Intent(
                    requireContext(),
                    CarnetBordUniversActivity::class.java
                )
            )
        }
        binding.tvQuestionTitre.text = getString(R.string.diner_titre_cambodge)
        binding.tvQuestionMessage.text = resources.getText(R.string.diner_cambodge_four_question)
        binding.animationQuestionLottie.visibility = View.GONE
        binding.animationQuestionImage.visibility = View.VISIBLE
        binding.animationQuestionImage.setImageResource(R.drawable.image_parchemin_mangue)
        binding.animationQuestionImage.contentDescription =
            getString(R.string.salle_diner_cambodge_mangue_accessibility)
        binding.layoutIndice.root.visibility = View.VISIBLE
        binding.layoutIndice.root.setOnClickListener {
            context?.let {
                Alerts.showClue(it, getString(R.string.diner_cambodge_four_indice)) {
                    updateClue()
                }
            }
        }
    }

    private fun updateClue() {
        gameProgress.updateClue()
        if (!gameProgress.isTrophyUnlocked(Trophies.TROPHY_CLUE_1)) {
            gameProgress.saveTrophy(Trophies.TROPHY_CLUE_1)
            context?.let {
                Alerts.showTrophyUnlocked(
                    it,
                    getString(Trophies.TROPHY_CLUE_1.message)
                )
            }
        }
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
        findNavController().navigate(R.id.action_cambodge_four_to_five)
    }
}
