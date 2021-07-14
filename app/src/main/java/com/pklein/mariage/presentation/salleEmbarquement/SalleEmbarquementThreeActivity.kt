package com.pklein.mariage.presentation.salleEmbarquement

import android.content.Intent
import android.os.Bundle
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.data.UniversViewModel
import com.pklein.mariage.databinding.ActivitySalleEmbarquementThreeBinding
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.CarnetBordActivity
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.utils.SHARED_PREFERENCE_KEY
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcher
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcherListener
import com.pklein.mariage.utils.uiUtils.PopinType

class SalleEmbarquementThreeActivity : BaseActivity(), CheckEmptyTextWatcherListener {

    private lateinit var binding: ActivitySalleEmbarquementThreeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySalleEmbarquementThreeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.SALLE_EMBARQUEMENT_3)
        UniversViewModel.storeUniversPage(
            SHARED_PREFERENCE_KEY.UNIVERS_1,
            LAST_ACTIVITY_LAUNCH.SALLE_EMBARQUEMENT_3
        )

        binding.ivHome.setOnClickListener {
            startActivity(Intent(this, CarnetBordActivity::class.java))
        }
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
            Alerts.showPopup(
                this,
                getString(R.string.embarquement_three_reponse_bravo),
                ::launchNext,
                PopinType.CLAPPING
            )
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
        UniversViewModel.finishUnivers(SHARED_PREFERENCE_KEY.UNIVERS_1)
        startActivity(Intent(this, CarnetBordActivity::class.java))
    }
}