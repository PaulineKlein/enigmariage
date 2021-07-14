package com.pklein.mariage.presentation.salleDiner.canada

import android.content.Intent
import android.os.Bundle
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.data.UniversViewModel
import com.pklein.mariage.databinding.ActivitySalleDinerCanadaSixBinding
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.CarnetBord2Activity
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.utils.SHARED_PREFERENCE_KEY
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcher
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcherListener
import com.pklein.mariage.utils.uiUtils.PopinType

class SalleDinerCanadaSixActivity : BaseActivity(), CheckEmptyTextWatcherListener {

    private lateinit var binding: ActivitySalleDinerCanadaSixBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySalleDinerCanadaSixBinding.inflate(layoutInflater)
        setContentView(binding.root)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.SALLE_DINER_CANADA_6)
        UniversViewModel.storeUniversPage(
            SHARED_PREFERENCE_KEY.UNIVERS_2_CANADA,
            LAST_ACTIVITY_LAUNCH.SALLE_DINER_CANADA_6
        )
        binding.ivHome.setOnClickListener {
            startActivity(Intent(this, CarnetBord2Activity::class.java))
        }
        binding.etDinerCanadaSixAnswer.addTextChangedListener(CheckEmptyTextWatcher(this))
        binding.buttonDinerCanadaSix.setOnClickListener {
            onValidateCLicked(binding.etDinerCanadaSixAnswer.text.toString())
        }
    }

    private fun onValidateCLicked(response: String) {
        if (response.formatAnswer() == "direction le japon") {
            Alerts.showPopup(
                this,
                getString(R.string.diner_canada_six_bravo),
                ::launchNext,
                PopinType.CLAPPING
            )
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
        UniversViewModel.finishUnivers(SHARED_PREFERENCE_KEY.UNIVERS_2_CANADA)
        startActivity(Intent(this, CarnetBord2Activity::class.java))
    }
}