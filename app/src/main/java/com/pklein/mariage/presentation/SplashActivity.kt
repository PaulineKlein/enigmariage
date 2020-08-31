package com.pklein.mariage.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.presentation.introduction.IntroductionActivity
import com.pklein.mariage.presentation.introduction.PasswordActivity
import com.pklein.mariage.utils.SHARED_PREFERENCE_KEY
import com.pklein.mariage.utils.SharedPreferenceStored
import com.pklein.mariage.utils.uiUtils.Alerts
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initButton()
    }

    private fun initButton() {
        val page = PlayerViewModel.getPage()

        if (page.isNullOrEmpty()) {
            button_nvlle_partie?.setOnClickListener {
                val intent = Intent(this, PasswordActivity::class.java)
                startActivity(intent)
            }
        } else {
            button_continuer?.isEnabled = true
            button_continuer?.setOnClickListener {
                SplashCoordinator.launchGame(page, this)
            }
            button_nvlle_partie?.setOnClickListener {
                showAlert()
            }
        }
    }

    private fun showAlert() {
        Alerts.showAlert(
            this,
            getString(R.string.attention),
            getString(R.string.popup_new_game),
            getString(R.string.oui),
            getString(R.string.non),
            ::launchNewGame
        )
    }

    private fun launchNewGame() {
        PlayerViewModel.resetStorage()
        val intent = Intent(this, PasswordActivity::class.java)
        startActivity(intent)
    }
}