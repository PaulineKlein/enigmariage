package com.pklein.mariage.presentation.salleCoktails

import android.content.Intent
import android.os.Bundle
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.data.UniversViewModel
import com.pklein.mariage.databinding.ActivitySalleCoktailsFiveBinding
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.CarnetBordActivity
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.utils.SHARED_PREFERENCE_KEY
import com.pklein.mariage.utils.uiUtils.Alerts

enum class CITY {
    GERUDO,
    COCORICO,
}

class SalleCoktailsFiveActivity : BaseActivity() {

    private lateinit var binding: ActivitySalleCoktailsFiveBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySalleCoktailsFiveBinding.inflate(layoutInflater)
        setContentView(binding.root)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.SALLE_COKTAIL_5)
        UniversViewModel.storeUniversPage(
            SHARED_PREFERENCE_KEY.UNIVERS_2_COCKTAILS,
            LAST_ACTIVITY_LAUNCH.SALLE_COKTAIL_5
        )
        binding.ivHome.setOnClickListener {
            startActivity(Intent(this, CarnetBordActivity::class.java))
        }
        binding.radioGroupMap.setOnCheckedChangeListener { _, _ ->
            binding.buttonCoktailFive.isEnabled = true
        }

        binding.ivDesert.setOnClickListener {
            binding.radioDesert.isChecked = true
            binding.radioVillage.isChecked = false
        }
        binding.ivVillage.setOnClickListener {
            binding.radioDesert.isChecked = false
            binding.radioVillage.isChecked = true
        }

        binding.buttonCoktailFive.setOnClickListener {
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
        val mapId = binding.radioGroupMap.checkedRadioButtonId
        return if (resources.getResourceEntryName(mapId) == "radioDesert") {
            CITY.GERUDO
        } else {
            CITY.COCORICO
        }
    }

    private fun launchNext() {
        startActivity(Intent(this, SalleCoktailsSixActivity::class.java))
    }
}