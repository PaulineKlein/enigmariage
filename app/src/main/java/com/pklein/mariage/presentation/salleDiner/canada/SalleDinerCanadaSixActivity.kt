package com.pklein.mariage.presentation.salleDiner.canada

import android.content.Intent
import android.os.Bundle
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.databinding.ActivitySalleDinerCanadaSixBinding
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.presentation.salleDiner.japon.SalleDinerJaponOneActivity
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcher
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcherListener

class SalleDinerCanadaSixActivity : BaseActivity(), CheckEmptyTextWatcherListener {

    private lateinit var binding: ActivitySalleDinerCanadaSixBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySalleDinerCanadaSixBinding.inflate(layoutInflater)
        setContentView(binding.root)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.SALLE_DINER_CANADA_6)

        binding.etDinerCanadaSixAnswer.addTextChangedListener(CheckEmptyTextWatcher(this))
        binding.buttonDinerCanadaSix.setOnClickListener {
            onValidateCLicked(binding.etDinerCanadaSixAnswer.text.toString())
        }
    }

    private fun onValidateCLicked(response: String) {
        if (response.formatAnswer() == "direction le japon") {
            // todo enlever la reponse à 1
            Alerts.showSuccess(this, ::launchNext)
        } else {
            Alerts.showError(this)
        }
    }

    override fun onTextEmpty() {
        binding.buttonDinerCanadaSix.isEnabled = false
    }

    override fun onTextNotEmpty() {
        binding.buttonDinerCanadaSix.isEnabled = true
    }

    private fun launchNext() {
        startActivity(Intent(this, SalleDinerJaponOneActivity::class.java))
    }
}