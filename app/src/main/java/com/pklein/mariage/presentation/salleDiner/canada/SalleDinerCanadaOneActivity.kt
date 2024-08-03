package com.pklein.mariage.presentation.salleDiner.canada

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.data.UniversViewModel
import com.pklein.mariage.presentation.CarnetBord2Activity
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.presentation.QuestionActivity
import com.pklein.mariage.utils.SHARED_PREFERENCE_KEY
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts

class SalleDinerCanadaOneActivity : QuestionActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.SALLE_DINER_CANADA_1)
        UniversViewModel.storeUniversPage(
            SHARED_PREFERENCE_KEY.UNIVERS_2_CANADA,
            LAST_ACTIVITY_LAUNCH.SALLE_DINER_CANADA_1
        )
        binding.ivHome.setOnClickListener {
            startActivity(Intent(this, CarnetBord2Activity::class.java))
        }
        binding.animationQuestionLottie.apply {
            visibility = View.VISIBLE
            setAnimation("animation_leaves.json")
            playAnimation()
            repeatCount = ValueAnimator.INFINITE
        }
        binding.tvQuestionTitre.text = getString(R.string.diner_titre_canada)
        binding.tvQuestionMessage.text = getText(R.string.diner_canada_one_message)
    }

    override fun onValidateCLicked(response: String) {
        if (response.formatAnswer() == "erable") {
            Alerts.showSuccess(this, ::launchNext)
        } else {
            Alerts.showError(this)
        }
    }

    override fun launchNext() {
        startActivity(Intent(this, SalleDinerCanadaTwoActivity::class.java))
    }
}