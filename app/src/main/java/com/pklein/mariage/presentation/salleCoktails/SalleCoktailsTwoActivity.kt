package com.pklein.mariage.presentation.salleCoktails

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.presentation.QuestionActivity
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts

class SalleCoktailsTwoActivity : QuestionActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.SALLE_COKTAIL_2)
        binding.animationQuestionLottie.setAnimation("animation_student.json")
        binding.animationQuestionLottie.playAnimation()
        binding.animationQuestionLottie.repeatCount = ValueAnimator.INFINITE
        binding.tvQuestionTitre.text = getString(R.string.coktail_one_titre)
        binding.tvQuestionMessage.text = getString(R.string.coktail_two_message)
        binding.layoutIndice.root.visibility = View.VISIBLE
        binding.layoutIndice.root.setOnClickListener {
            Alerts.showClue(this, getString(R.string.coktail_two_message_indice))
        }
    }

    override fun onValidateCLicked(response: String) {
        if (response.formatAnswer() == "saint etienne" || response.formatAnswer() == "saint-etienne") {
            Alerts.showSuccess(this, ::launchNext)
        } else {
            Alerts.showError(this)
        }
    }

    override fun launchNext() {
        startActivity(Intent(this, SalleCoktailsThreeActivity::class.java))
    }
}