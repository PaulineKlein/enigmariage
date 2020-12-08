package com.pklein.mariage.presentation.salleDiner.japon

import android.content.Intent
import android.os.Bundle
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.databinding.ActivitySalleDinerJaponFourBinding
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcher
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcherListener

class SalleDinerJaponFourActivity : BaseActivity(), CheckEmptyTextWatcherListener {

    private lateinit var binding: ActivitySalleDinerJaponFourBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySalleDinerJaponFourBinding.inflate(layoutInflater)
        setContentView(binding.root)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.SALLE_DINER_JAPON_4)

        binding.etDinerJaponFourAnswer.addTextChangedListener(CheckEmptyTextWatcher(this))
        binding.buttonDinerJaponFour.setOnClickListener {
            onValidateCLicked(binding.etDinerJaponFourAnswer.text.toString())
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
        binding.buttonDinerJaponFour.isEnabled = false
    }

    override fun onTextNotEmpty() {
        binding.buttonDinerJaponFour.isEnabled = true
    }

    private fun launchNext() {
        startActivity(Intent(this, SalleDinerJaponFiveActivity::class.java))
    }
}