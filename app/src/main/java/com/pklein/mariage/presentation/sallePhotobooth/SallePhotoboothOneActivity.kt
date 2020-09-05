package com.pklein.mariage.presentation.sallePhotobooth

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.presentation.QuestionActivity
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts
import kotlinx.android.synthetic.main.activity_question.*

class SallePhotoboothOneActivity : QuestionActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.SALLE_PHOTOBOOTH_1)
        tv_question_titre?.text = getString(R.string.photobooth_one_titre2)
        tv_question_message?.text = getString(R.string.photobooth_one_enigme)

        animationQuestion_lottie?.visibility = View.GONE
        animationQuestion_image?.visibility = View.VISIBLE
        animationQuestion_image?.setImageResource(R.drawable.image_parchemin_photomaton)
    }

    override fun onValidateCLicked(response: String) {
        if (response.formatAnswer() == "photomaton") {
            Alerts.showSuccess(this, ::launchNext)
        } else {
            Alerts.showError(this)
        }
    }

    override fun launchNext() {
        startActivity(Intent(this, SallePhotoboothTwoActivity::class.java))
    }
}