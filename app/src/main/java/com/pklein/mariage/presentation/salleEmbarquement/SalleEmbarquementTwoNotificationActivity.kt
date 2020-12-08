package com.pklein.mariage.presentation.salleEmbarquement

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.databinding.ActivitySalleEmbarquementTwoNotificationBinding
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.utils.Notification

class SalleEmbarquementTwoNotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySalleEmbarquementTwoNotificationBinding
    private var counter: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_salle_embarquement_two_notification)
        setContentView(binding.root)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.SALLE_EMBARQUEMENT_2)

        binding.buttonEmbarquementTwoResend.setOnClickListener {
            counter += 1
            launchNotification()
            if (counter >= 3) {
                binding.tvEmbarquementTwoNotificationRetry3.visibility = View.VISIBLE
                binding.tvEmbarquementTwoNotificationRetry3.setOnClickListener {
                    val intent = Intent(this, SalleEmbarquementThreeActivity::class.java)
                    startActivity(intent)
                }
            } else {
                binding.tvEmbarquementTwoNotificationRetry3.visibility = View.INVISIBLE
            }
        }
    }

    private fun launchNotification() {
        Notification.cancelNotification(this)
        Notification.sendNotificationSalleEmbarquement(this)
    }
}