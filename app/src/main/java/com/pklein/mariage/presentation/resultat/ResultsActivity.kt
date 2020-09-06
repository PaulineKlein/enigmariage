package com.pklein.mariage.presentation.resultat

import android.content.Intent
import android.os.Bundle
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.presentation.SplashActivity
import kotlinx.android.synthetic.main.activity_results.*

class ResultsActivity : BaseActivity() {

    private val resultsViewModel = ResultsViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.RESULT)

        tv_resultat_temps_reponse.text =
            resultsViewModel.calculateTime() ?: getString(R.string.erreurs)
        tv_resultat_score_reponse.text = resultsViewModel.calculateScore()

        button_resultat?.setOnClickListener {
            startActivity(Intent(this, SplashActivity::class.java))
        }
    }
}