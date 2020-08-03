package com.pklein.mariage.presentation.salleEmbarquement

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.utils.Notification
import kotlinx.android.synthetic.main.activity_salle_emarquement_two.*

class SalleEmbarquementTwoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salle_emarquement_two)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.SALLE_EMBARQUEMENT_2)

        button_embarquement_two.setOnClickListener {
            launchNotification()
        }
    }

    private fun launchNotification() {
        startActivity(Intent(this, SalleEmbarquementTwoNotificationActivity::class.java))
        Notification.sendNotificationSalleEmbarquement(this)
    }
}