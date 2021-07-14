package com.pklein.mariage.presentation.fairePart

import android.content.Intent
import android.os.Bundle
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.data.UniversViewModel
import com.pklein.mariage.databinding.ActivityFairePartBinding
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.CarnetBordActivity
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.utils.SHARED_PREFERENCE_KEY
import com.pklein.mariage.utils.uiUtils.Alerts

class FairePartActivity : BaseActivity() {

    private lateinit var binding: ActivityFairePartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFairePartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.FAIRE_PART)
        UniversViewModel.storeUniversPage(
            SHARED_PREFERENCE_KEY.UNIVERS_1,
            LAST_ACTIVITY_LAUNCH.FAIRE_PART
        )
        binding.ivHome.setOnClickListener {
            startActivity(Intent(this, CarnetBordActivity::class.java))
        }

        binding.buttonKanji.setOnClickListener {
            val kanjiId = binding.radioGroupKanji.checkedRadioButtonId
            if (kanjiId == R.id.radioReponse1) {
                Alerts.showSuccess(this, ::launchNext)
            } else {
                Alerts.showError(this)
            }
        }
    }

    private fun launchNext() {
        startActivity(Intent(this, FairePartResponseActivity::class.java))
    }
}