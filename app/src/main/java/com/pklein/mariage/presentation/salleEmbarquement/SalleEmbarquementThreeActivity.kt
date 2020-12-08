package com.pklein.mariage.presentation.salleEmbarquement

import android.content.Intent
import android.os.Bundle
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.databinding.ActivitySalleEmbarquementThreeBinding
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.presentation.salleCoktails.SalleCoktailsOneActivity
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcher
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcherListener

class SalleEmbarquementThreeActivity : BaseActivity(), CheckEmptyTextWatcherListener {

    private lateinit var binding: ActivitySalleEmbarquementThreeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySalleEmbarquementThreeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.SALLE_EMBARQUEMENT_3)

        binding.etEmbarquementThreeAnswer.addTextChangedListener(CheckEmptyTextWatcher(this))
        binding.buttonEmbarquementThree.setOnClickListener {
            onValidateCLicked(binding.etEmbarquementThreeAnswer.text.toString())
        }

        binding.layoutIndice.root.setOnClickListener {
            Alerts.showClue(this, getString(R.string.embarquement_three_clue))
        }
    }

    private fun onValidateCLicked(response: String) {
        if (response.formatAnswer() == "va dans la salle de cocktails") {
            // todo enlever la reponse à 1
            Alerts.showSuccess(this, ::launchNext)
        } else {
            Alerts.showError(this)
        }
    }

    override fun onTextEmpty() {
        binding.buttonEmbarquementThree.isEnabled = false
    }

    override fun onTextNotEmpty() {
        binding.buttonEmbarquementThree.isEnabled = true
    }

    private fun launchNext() {
        startActivity(Intent(this, SalleCoktailsOneActivity::class.java))
    }
}