package com.pklein.mariage.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.pklein.mariage.R
import com.pklein.mariage.utils.CountDown
import com.pklein.mariage.utils.SHARED_PREFERENCE_KEY
import com.pklein.mariage.utils.SharedPreferenceStored
import com.pklein.mariage.utils.uiUtils.Alerts

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.slide_in, R.anim.neutral)

        CountDown.updateNbOfCountDown.observe(this, Observer {
            it.getContentIfNotHandled()?.let {nb ->
                it.hasBeenHandled = true
                Alerts.showCountDown(this, nb)
            }
        })
    }

    override fun onBackPressed() {
        finish()
        overridePendingTransition(R.anim.neutral, R.anim.slide_out)
    }

    protected fun launchCountDown() {
        val actualNbOfCountDown =
            SharedPreferenceStored.getValue(SHARED_PREFERENCE_KEY.COUNT_DOWN)

        if (actualNbOfCountDown.isNullOrEmpty() || (!actualNbOfCountDown.isNullOrEmpty() && actualNbOfCountDown.toInt() < 11)) {
            CountDown.start()
        }
    }
}