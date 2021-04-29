package com.pklein.mariage.presentation.salleDiner.japon

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.presentation.QuestionActivity
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts

class SalleDinerJaponOneActivity : QuestionActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.SALLE_DINER_JAPON_1)
        binding.animationQuestionLottie.setAnimation("animation_japan.json")
        binding.animationQuestionLottie.playAnimation()
        binding.animationQuestionLottie.repeatCount = ValueAnimator.INFINITE
        binding.tvQuestionTitre.text = getString(R.string.diner_titre_japon)
        binding.tvQuestionMessage.text = getText(R.string.diner_japon_one_question)
        binding.etQuestionAnswer.inputType = EditorInfo.TYPE_CLASS_NUMBER

        binding.layoutIndice.root.visibility = View.VISIBLE
        binding.layoutIndice.root.setOnClickListener {
            Alerts.showClue(this, getString(R.string.diner_japon_one_help))
        }
    }

    override fun onValidateCLicked(response: String) {
        if (response.formatAnswer() == "2015") {
            Alerts.showSuccess(this, ::launchNext)
        } else {
            Alerts.showError(this)
        }
    }

    override fun launchNext() {
        startActivity(Intent(this, SalleDinerJaponTwoActivity::class.java))
    }
}