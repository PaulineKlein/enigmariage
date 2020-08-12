package com.pklein.mariage.presentation.salleDiner.canada

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcher
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcherListener
import kotlinx.android.synthetic.main.activity_question.*

class SalleDinerCanadaTwoActivity : AppCompatActivity(), CheckEmptyTextWatcherListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salle_diner_canada_two)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.SALLE_DINER_CANADA_2)

        et_question_answer?.addTextChangedListener(CheckEmptyTextWatcher(this))
        button_question?.setOnClickListener {
            onValidateCLicked(et_question_answer?.text.toString())
        }
    }

    fun onValidateCLicked(response: String) {
        if (response.formatAnswer() == "rouge") {
            Alerts.showSuccess(this, ::launchNext)
        } else {
            Alerts.showError(this)
        }
    }

    fun launchNext() {
        startActivity(Intent(this, SalleDinerCanadaTwoActivity::class.java))
    }

    override fun onTextEmpty() {
        button_question?.isEnabled = false
    }

    override fun onTextNotEmpty() {
        button_question?.isEnabled = true
    }
}