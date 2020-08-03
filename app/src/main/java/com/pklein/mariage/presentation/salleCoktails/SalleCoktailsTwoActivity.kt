package com.pklein.mariage.presentation.salleCoktails

import android.content.Intent
import android.os.Bundle
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.presentation.QuestionActivity
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts
import kotlinx.android.synthetic.main.activity_question.*

class SalleCoktailsTwoActivity : QuestionActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.SALLE_COKTAIL_2)
        tv_question_titre?.text = getString(R.string.coktail_one_titre)
        tv_question_message?.text = getString(R.string.coktail_two_message)
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