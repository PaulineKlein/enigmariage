package com.pklein.mariage.presentation.salleEmbarquement

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.data.UniversViewModel
import com.pklein.mariage.databinding.ActivitySalleEmbarquementTwoBinding
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.CarnetBordActivity
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.utils.Notification
import com.pklein.mariage.utils.SHARED_PREFERENCE_KEY

class SalleEmbarquementTwoActivity : BaseActivity() {

    private lateinit var binding: ActivitySalleEmbarquementTwoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_salle_embarquement_two)
        setContentView(binding.root)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.SALLE_EMBARQUEMENT_2)
        UniversViewModel.storeUniversPage(
            SHARED_PREFERENCE_KEY.UNIVERS_1,
            LAST_ACTIVITY_LAUNCH.SALLE_EMBARQUEMENT_2
        )

        binding.ivHome.setOnClickListener {
            startActivity(Intent(this, CarnetBordActivity::class.java))
        }
        binding.buttonEmbarquementTwo.setOnClickListener {
            launchNotification()
        }
    }

    private fun launchNotification() {
        startActivity(Intent(this, SalleEmbarquementTwoNotificationActivity::class.java))
        Notification.sendNotificationSalleEmbarquement(this)
    }
}