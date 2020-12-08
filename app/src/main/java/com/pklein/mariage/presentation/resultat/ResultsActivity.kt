package com.pklein.mariage.presentation.resultat

import android.content.Intent
import android.os.Bundle
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.databinding.ActivityResultsBinding
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.presentation.SplashActivity

class ResultsActivity : BaseActivity() {

    private lateinit var binding: ActivityResultsBinding
    private val resultsViewModel = ResultsViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.RESULT)

        binding.tvResultatTempsReponse.text =
            resultsViewModel.calculateTime() ?: getString(R.string.erreurs)
        binding.tvResultatScoreReponse.text = resultsViewModel.calculateScore()

        binding.buttonResultat.setOnClickListener {
            startActivity(Intent(this, SplashActivity::class.java))
        }
    }
}