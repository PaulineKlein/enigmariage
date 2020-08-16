package com.pklein.mariage.presentation.introduction

import android.content.Intent
import android.os.Bundle
import com.pklein.mariage.R
import com.pklein.mariage.presentation.QuestionActivity
import com.pklein.mariage.utils.SharedPreferenceStored
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts
import com.pklein.mariage.utils.uiUtils.PopinType
import kotlinx.android.synthetic.main.activity_question.*

class PasswordActivity : QuestionActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedPreferenceStored.resetPreference()

        tv_question_titre?.text = getString(R.string.password_titre)
        tv_question_message?.text = getString(R.string.password_message)
    }

    override fun onValidateCLicked(response: String) {
        if (response.formatAnswer() == "kogenheim") {
            Alerts.showPopup(
                this,
                getString(R.string.merci),
                ::launchNext,
                PopinType.CLAPPING
            )
        } else {
            Alerts.showError(this)
        }
    }

    override fun launchNext() {
        startActivity(Intent(this, IntroductionActivity::class.java))
    }
}