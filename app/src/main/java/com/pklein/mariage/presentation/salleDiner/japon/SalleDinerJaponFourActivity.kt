package com.pklein.mariage.presentation.salleDiner.japon

import android.content.Intent
import android.os.Bundle
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcher
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcherListener
import kotlinx.android.synthetic.main.activity_salle_diner_japon_four.*

class SalleDinerJaponFourActivity : BaseActivity(), CheckEmptyTextWatcherListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salle_diner_japon_four)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.SALLE_DINER_JAPON_4)

        et_diner_japon_four_answer?.addTextChangedListener(CheckEmptyTextWatcher(this))
        button_diner_japon_four?.setOnClickListener {
            onValidateCLicked(et_diner_japon_four_answer?.text.toString())
        }
    }

    private fun onValidateCLicked(response: String) {
        if (response.formatAnswer() == "bubble tea" || response.formatAnswer() == "bubbletea") {
            Alerts.showSuccess(this, ::launchNext)
        } else {
            Alerts.showError(this)
        }
    }

    override fun onTextEmpty() {
        button_diner_japon_four?.isEnabled = false
    }

    override fun onTextNotEmpty() {
        button_diner_japon_four?.isEnabled = true
    }

    private fun launchNext() {
        startActivity(Intent(this, SalleDinerJaponFiveActivity::class.java))
    }
}