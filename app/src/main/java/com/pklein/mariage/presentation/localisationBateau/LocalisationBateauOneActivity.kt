package com.pklein.mariage.presentation.localisationBateau

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.presentation.QuestionActivity
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts
import kotlinx.android.synthetic.main.activity_question.*

class LocalisationBateauOneActivity : QuestionActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.LOCALISATION_BATEAU_1)
        tv_question_titre?.text = getString(R.string.diner_titre_localisation)
        tv_question_message?.text = getString(R.string.localisation_one_question)

        animationQuestion_lottie?.visibility = View.GONE
        animationQuestion_image?.visibility = View.VISIBLE
        animationQuestion_image?.setImageResource(R.drawable.image_parchemin_sirene)

        layout_indice?.visibility = View.VISIBLE
        layout_indice?.setOnClickListener {
            Alerts.showClue(this, getString(R.string.localisation_one_indice))
        }
    }

    override fun onValidateCLicked(response: String) {
        if (response.formatAnswer() == "sirene" || response.formatAnswer() == "sirenes") {
            Alerts.showSuccess(this, ::launchNext)
        } else {
            Alerts.showError(this)
        }
    }

    override fun launchNext() {
        startActivity(Intent(this, LocalisationBateauTwoActivity::class.java))
    }
}