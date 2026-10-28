package com.pklein.mariage.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.pklein.mariage.R
import com.pklein.mariage.databinding.ActivitySplashBinding
import com.pklein.mariage.presentation.introduction.IntroductionActivity
import com.pklein.mariage.presentation.trophies.TrophyActivity
import com.pklein.mariage.utils.extension.addSystemWindowInsetToMargin
import com.pklein.mariage.utils.uiUtils.Alerts
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity() {
    lateinit var binding: ActivitySplashBinding
    private val gameProgress: GameProgressViewModel by viewModels()

    private val requestNotificationPermission =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { /* permission granted or denied — nothing to do */ }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.scrollRoot.addSystemWindowInsetToMargin()
        requestNotificationPermissionIfNeeded()
        initButton()
        animateEntrance()
    }

    // singleTask reuses the existing instance instead of calling onCreate, so rebuild the screen to show fresh statuses.
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        initButton()
    }

    private fun animateEntrance() {
        val offset = 60f
        listOf(
            binding.tvSplashTitre,
            binding.tvSplashTagline,
            binding.ivSplashBateau,
            binding.linearButtons,
        ).forEachIndexed { i, view ->
            view.let {
                it.translationY = offset
                it
                    .animate()
                    .alpha(1f)
                    .translationY(0f)
                    .setStartDelay(120L * i)
                    .setDuration(500)
                    .start()
            }
        }
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
        if (gameProgress.page.isNullOrEmpty()) {
            binding.buttonNvllePartie.setOnClickListener {
                val intent = Intent(this, IntroductionActivity::class.java)
                startActivity(intent)
            }
        } else {
            binding.buttonContinuer.isEnabled = true
            binding.buttonContinuer.setOnClickListener {
                gameProgress.page?.let {
                    SplashCoordinator.launchGame(it, this)
                    launchCountDown()
                }
            }
            binding.buttonNvllePartie.setOnClickListener {
                showAlert()
            }
        }
        binding.buttonTrophees.setOnClickListener {
            startActivity(Intent(this, TrophyActivity::class.java))
        }
    }

    private fun showAlert() {
        Alerts.showAlert(
            this,
            getString(R.string.attention),
            getString(R.string.popup_new_game),
            getString(R.string.oui),
            getString(R.string.non),
            ::launchNewGame,
        )
    }

    private fun launchNewGame() {
        gameProgress.resetStorage()
        val intent = Intent(this, IntroductionActivity::class.java)
        startActivity(intent)
    }
}
