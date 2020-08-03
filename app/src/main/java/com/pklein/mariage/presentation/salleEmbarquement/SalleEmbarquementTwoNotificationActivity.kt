package com.pklein.mariage.presentation.salleEmbarquement

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.utils.Notification
import kotlinx.android.synthetic.main.activity_salle_embarquement_two_notification.*

class SalleEmbarquementTwoNotificationActivity : AppCompatActivity() {
    private var counter: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salle_embarquement_two_notification)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.SALLE_EMBARQUEMENT_2)

        button_embarquement_two_resend.setOnClickListener {
            counter += 1
            launchNotification()
            if (counter >= 3) {
                tv_embarquement_two_notification_retry_3.visibility = View.VISIBLE
                tv_embarquement_two_notification_retry_3.setOnClickListener {
                    val intent = Intent(this, SalleEmbarquementThreeActivity::class.java)
                    startActivity(intent)
                }
            } else {
                tv_embarquement_two_notification_retry_3.visibility = View.INVISIBLE
            }
        }
    }

    private fun launchNotification() {
        Notification.cancelNotification(this)
        Notification.sendNotificationSalleEmbarquement(this)
    }
}