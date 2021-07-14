package com.pklein.mariage.presentation.combatFinal

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.data.UniversViewModel
import com.pklein.mariage.databinding.ActivityCombatFinalOneBinding
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.CarnetBordActivity
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.utils.Notification
import com.pklein.mariage.utils.SHARED_PREFERENCE_KEY

class CombatFinalOneActivity : BaseActivity() {

    private lateinit var binding: ActivityCombatFinalOneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_combat_final_one)
        setContentView(binding.root)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.COMBAT_FINAL_1)
        UniversViewModel.storeUniversPage(
            SHARED_PREFERENCE_KEY.UNIVERS_3,
            LAST_ACTIVITY_LAUNCH.COMBAT_FINAL_1
        )
        binding.ivHome.setOnClickListener {
            startActivity(Intent(this, CarnetBordActivity::class.java))
        }
        launchNotification()

        binding.buttonCombatFinalOne.setOnClickListener {
            startActivity(Intent(this, CombatFinalTwoActivity::class.java))
        }
    }

    private fun launchNotification() {
        Notification.cancelNotification(this)
        Notification.sendNotificationCombatFinal(this)
    }
}