package com.pklein.mariage.presentation.trophies

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pklein.mariage.R
import com.pklein.mariage.data.model.Trophies
import com.pklein.mariage.databinding.ActivityTrophyBinding
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.utils.extension.addSystemWindowInsetToMargin
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrophyActivity : BaseActivity() {
    private lateinit var binding: ActivityTrophyBinding
    private val gameProgress: GameProgressViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrophyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.addSystemWindowInsetToMargin()

        binding.ivBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        // Platine Trophy
        val totalTrophiesCount = Trophies.entries.size
        if (
            gameProgress.unlockedTrophiesCount == totalTrophiesCount - 1 &&
            !gameProgress.isTrophyUnlocked(Trophies.TROPHY_PLATINE)
        ) {
            gameProgress.saveTrophy(Trophies.TROPHY_PLATINE)
        }

        val trophies = gameProgress.getAllTrophies()
        val missingCount = trophies.count { !it.second }
        binding.tvTrophySubtitle.text =
            if (missingCount == 0) {
                getString(R.string.trophies_complete)
            } else {
                resources.getQuantityString(R.plurals.trophies_missing, missingCount, missingCount)
            }

        binding.rvTrophies.layoutManager = LinearLayoutManager(this)
        binding.rvTrophies.adapter = TrophyAdapter(trophies)
    }

    // singleTask reuses the existing instance instead of calling onCreate, so rebuild the screen to show fresh statuses.
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        recreate()
    }
}
