package com.pklein.mariage.presentation.salleCoktails

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.data.UniversViewModel
import com.pklein.mariage.databinding.ActivitySalleCoktailsOneBinding
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.CarnetBordActivity
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.utils.SHARED_PREFERENCE_KEY
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts
import com.pklein.mariage.utils.uiUtils.CadenaLayoutListener
import com.pklein.mariage.utils.uiUtils.PopinType

class SalleCoktailsOneActivity : BaseActivity(), CadenaLayoutListener {

    private lateinit var binding: ActivitySalleCoktailsOneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_salle_coktails_one)
        setContentView(binding.root)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.SALLE_COKTAIL_1)
        UniversViewModel.storeUniversPage(
            SHARED_PREFERENCE_KEY.UNIVERS_2_COCKTAILS,
            LAST_ACTIVITY_LAUNCH.SALLE_COKTAIL_1
        )
        binding.ivHome.setOnClickListener {
            startActivity(Intent(this, CarnetBordActivity::class.java))
        }
    }

    override fun onValidateCLicked(response: String) {
        if (response.formatAnswer() == "2010") {
            Alerts.showSuccess(this, ::launchNext, PopinType.UNLOCK_SUCCESS)
        } else {
            Alerts.showError(this)
        }
    }

    private fun launchNext() {
        startActivity(Intent(this, SalleCoktailsTwoActivity::class.java))
    }
}