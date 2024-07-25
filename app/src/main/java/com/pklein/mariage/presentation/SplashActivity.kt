package com.pklein.mariage.presentation

import android.content.Intent
import android.os.Bundle
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.databinding.ActivitySplashBinding
import com.pklein.mariage.presentation.introduction.IntroductionActivity
import com.pklein.mariage.utils.uiUtils.Alerts


class SplashActivity : BaseActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initButton()
    }

    private fun initButton() {
        val page = PlayerViewModel.getPage()

        if (page.isNullOrEmpty()) {
            binding.buttonNvllePartie.setOnClickListener {
                val intent = Intent(this, IntroductionActivity::class.java)
                startActivity(intent)
            }
        } else {
            binding.buttonContinuer.isEnabled = true
            binding.buttonContinuer.setOnClickListener {
                SplashCoordinator.launchGame(page, this)
                launchCountDown()
            }
            binding.buttonNvllePartie.setOnClickListener {
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
        val intent = Intent(this, IntroductionActivity::class.java)
        startActivity(intent)
    }
}