package com.pklein.mariage.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.databinding.ActivitySplashBinding
import com.pklein.mariage.presentation.introduction.IntroductionActivity
import com.pklein.mariage.utils.extension.addSystemWindowInsetToMargin
import com.pklein.mariage.utils.uiUtils.Alerts


class SplashActivity : BaseActivity() {

    lateinit var binding: ActivitySplashBinding

    private val requestNotificationPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { /* permission granted or denied — nothing to do */ }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestNotificationPermissionIfNeeded()
        initButton()
    }

    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestNotificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
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
        binding.scrollRoot.addSystemWindowInsetToMargin()
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