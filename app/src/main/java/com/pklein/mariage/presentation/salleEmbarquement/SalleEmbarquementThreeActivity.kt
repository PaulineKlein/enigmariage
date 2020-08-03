package com.pklein.mariage.presentation.salleEmbarquement

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.presentation.salleCoktails.SalleCoktailsOneActivity
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcher
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcherListener
import com.pklein.mariage.utils.uiUtils.PopinType
import kotlinx.android.synthetic.main.activity_salle_embarquement_three.*

class SalleEmbarquementThreeActivity : AppCompatActivity(), CheckEmptyTextWatcherListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salle_embarquement_three)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.SALLE_EMBARQUEMENT_3)

        et_embarquement_three_answer?.addTextChangedListener(CheckEmptyTextWatcher(this))
        button_embarquement_three?.setOnClickListener {
            onValidateCLicked(et_embarquement_three_answer?.text.toString())
        }
    }

    private fun onValidateCLicked(response: String) {
        if (response.formatAnswer() == "va dans la salle de coktails" || response == "1") {
            // todo enlever la reponse à 1
            Alerts.showSuccess(this,::launchNext)
        } else {
            Alerts.showError(this)
        }
    }

    override fun onTextEmpty() {
        button_embarquement_three?.isEnabled = false
    }

    override fun onTextNotEmpty() {
        button_embarquement_three?.isEnabled = true
    }

    private fun launchNext() {
        startActivity(Intent(this, SalleCoktailsOneActivity::class.java))
    }
}