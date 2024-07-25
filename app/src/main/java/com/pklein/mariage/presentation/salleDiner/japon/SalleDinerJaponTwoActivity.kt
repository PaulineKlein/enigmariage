package com.pklein.mariage.presentation.salleDiner.japon

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

class SalleDinerJaponTwoActivity : QuestionActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.SALLE_DINER_JAPON_2)
        UniversViewModel.storeUniversPage(
            SHARED_PREFERENCE_KEY.UNIVERS_2_JAPON,
            LAST_ACTIVITY_LAUNCH.SALLE_DINER_JAPON_2
        )
        binding.ivHome.setOnClickListener {
            startActivity(Intent(this, CarnetBord2Activity::class.java))
        }
        binding.tvQuestionTitre.text = getString(R.string.diner_titre_japon)
        binding.tvQuestionMessage.text = getText(R.string.diner_japon_two_question)

        binding.animationQuestionLottie.visibility = View.GONE
        binding.animationQuestionImage.visibility = View.VISIBLE
        binding.animationQuestionImage.setImageResource(R.drawable.image_video_games)
    }

    override fun onValidateCLicked(response: String) {
        if (response.formatAnswer() == "chiba") {
            Alerts.showSuccess(this, ::launchNext)
        } else {
            Alerts.showError(this)
        }
    }

    override fun launchNext() {
        startActivity(Intent(this, SalleDinerJaponThreeActivity::class.java))
    }
}