package com.pklein.mariage.presentation.salleDiner.canada

import android.content.Intent
import android.os.Bundle
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.presentation.salleDiner.japon.SalleDinerJaponOneActivity
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcher
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcherListener
import kotlinx.android.synthetic.main.activity_salle_diner_canada_six.*

class SalleDinerCanadaSixActivity : BaseActivity(), CheckEmptyTextWatcherListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salle_diner_canada_six)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.SALLE_DINER_CANADA_6)

        et_diner_canada_six_answer?.addTextChangedListener(CheckEmptyTextWatcher(this))
        button_diner_canada_six?.setOnClickListener {
            onValidateCLicked(et_diner_canada_six_answer?.text.toString())
        }
    }

    private fun onValidateCLicked(response: String) {
        if (response.formatAnswer() == "direction le japon" || response == "1") {
            // todo enlever la reponse à 1
            Alerts.showSuccess(this, ::launchNext)
        } else {
            Alerts.showError(this)
        }
    }

    override fun onTextEmpty() {
        button_diner_canada_six?.isEnabled = false
    }

    override fun onTextNotEmpty() {
        button_diner_canada_six?.isEnabled = true
    }

    private fun launchNext() {
        startActivity(Intent(this, SalleDinerJaponOneActivity::class.java))
    }
}