package com.pklein.mariage.presentation.salleEmbarquement

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.databinding.ActivitySalleEmbarquementTwoBinding
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.utils.Notification

class SalleEmbarquementTwoActivity : BaseActivity() {

    private lateinit var binding: ActivitySalleEmbarquementTwoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_salle_embarquement_two)
        setContentView(binding.root)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.SALLE_EMBARQUEMENT_2)

        binding.buttonEmbarquementTwo.setOnClickListener {
            launchNotification()
        }
    }

    private fun launchNotification() {
        startActivity(Intent(this, SalleEmbarquementTwoNotificationActivity::class.java))
        Notification.sendNotificationSalleEmbarquement(this)
    }
}