package com.pklein.mariage.presentation.combatFinal

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.utils.Notification
import kotlinx.android.synthetic.main.activity_combat_final_one.*

class CombatFinalOneActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_combat_final_one)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.COMBAT_FINAL_1)

        launchNotification()

        button_combatFinal_one?.setOnClickListener {
            startActivity(Intent(this, CombatFinalOneActivity::class.java))
        }
    }

    private fun launchNotification() {
        Notification.cancelNotification(this)
        Notification.sendNotificationCombatFinal(this)
    }
}