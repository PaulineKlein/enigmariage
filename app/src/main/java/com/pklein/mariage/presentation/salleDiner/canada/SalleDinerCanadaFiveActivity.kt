package com.pklein.mariage.presentation.salleDiner.canada

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.presentation.QuestionActivity
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts
import kotlinx.android.synthetic.main.activity_question.*

class SalleDinerCanadaFiveActivity : QuestionActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.SALLE_DINER_CANADA_5)
        tv_question_titre?.text = getString(R.string.diner_one_titre)
        tv_question_message?.text = getString(R.string.diner_canada_five_message)
        et_question_answer?.inputType = EditorInfo.TYPE_CLASS_NUMBER
    }

    override fun onValidateCLicked(response: String) {
        if (response.formatAnswer() == "3840") {
            Alerts.showSuccess(this, ::launchNext)
        } else {
            Alerts.showError(this)
        }
    }

    override fun launchNext() {
        startActivity(Intent(this, SalleDinerCanadaFiveActivity::class.java))
    }
}