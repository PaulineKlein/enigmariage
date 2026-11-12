package com.pklein.mariage.presentation.localisationBateau

import android.view.View
import androidx.navigation.fragment.findNavController
import com.pklein.mariage.R
import com.pklein.mariage.data.model.SharedPreferenceKey
import com.pklein.mariage.databinding.FragmentQuestionBinding
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.QuestionFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocalisationBateauOneFragment : QuestionFragment() {
    override val gameStep = GameStep.LocalisationBateau1
    override val universStep = SharedPreferenceKey.UNIVERS_1

    override fun onSetupView(binding: FragmentQuestionBinding) {
        binding.tvQuestionTitre.text = getString(R.string.diner_titre_localisation)
        binding.animationQuestionLottie.visibility = View.GONE
        binding.animationQuestionImage.visibility = View.VISIBLE
        binding.layoutIndice.root.visibility = View.VISIBLE
    }

    override fun launchNext() {
        findNavController().navigate(R.id.action_localisation_one_to_two)
    }
}
