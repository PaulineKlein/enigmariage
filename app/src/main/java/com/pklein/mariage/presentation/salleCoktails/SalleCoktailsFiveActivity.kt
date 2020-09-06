package com.pklein.mariage.presentation.salleCoktails

import android.content.Intent
import android.os.Bundle
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.utils.uiUtils.Alerts
import kotlinx.android.synthetic.main.activity_salle_coktails_five.*

enum class CITY {
    GERUDO,
    COCORICO,
}

class SalleCoktailsFiveActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salle_coktails_five)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.SALLE_COKTAIL_5)

        radio_group_map?.setOnCheckedChangeListener { _, _ ->
            button_coktail_five?.isEnabled = true
        }

        button_coktail_five?.setOnClickListener {
            onValidateCLicked(checkValueFromRadioButton())
        }
    }

    private fun onValidateCLicked(city: CITY) {
        if (city == CITY.GERUDO) {
            Alerts.showSuccess(this, ::launchNext)
        } else {
            Alerts.showError(this)
        }
    }

    private fun checkValueFromRadioButton(): CITY {
        val mapId = radio_group_map.checkedRadioButtonId
        return if (resources.getResourceEntryName(mapId) == "radioDesert")
        {
            CITY.GERUDO
        } else {
            CITY.COCORICO
        }
    }

    private fun launchNext() {
        startActivity(Intent(this, SalleCoktailsSixActivity::class.java))
    }
}