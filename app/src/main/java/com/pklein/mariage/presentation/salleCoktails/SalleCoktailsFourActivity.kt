package com.pklein.mariage.presentation.salleCoktails

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.presentation.QuestionActivity
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts

class SalleCoktailsFourActivity : QuestionActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.SALLE_COKTAIL_4)
        binding.animationQuestionLottie.setAnimation("animation_green.json")
        binding.animationQuestionLottie.playAnimation()
        binding.animationQuestionLottie.repeatCount = ValueAnimator.INFINITE
        binding.tvQuestionTitre.text = getString(R.string.coktail_one_titre)
        binding.tvQuestionMessage.text = getString(R.string.coktail_four_message)
    }

    override fun onValidateCLicked(response: String) {
        if (response.formatAnswer() == "cactus") {
            Alerts.showSuccess(this, ::launchNext)
        } else {
            Alerts.showError(this)
        }
    }

    override fun launchNext() {
        startActivity(Intent(this, SalleCoktailsFiveActivity::class.java))
    }
}