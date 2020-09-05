package com.pklein.mariage.presentation.localisationBateau

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.presentation.QuestionActivity
import com.pklein.mariage.presentation.fairePart.FairePartActivity
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts
import kotlinx.android.synthetic.main.activity_question.*

class LocalisationBateauThreeActivity : QuestionActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.LOCALISATION_BATEAU_3)
        tv_question_titre?.text = getString(R.string.faire_part_titre)
        tv_question_message?.text = getString(R.string.localisation_three_question)

        animationQuestion_lottie?.visibility = View.GONE
        animationQuestion_image?.visibility = View.VISIBLE
        animationQuestion_image?.setImageResource(R.drawable.image_parchemin_temoin)

        layout_indice?.visibility = View.VISIBLE
        layout_indice?.setOnClickListener {
            Alerts.showClue(this, getString(R.string.localisation_three_indice))
        }
    }

    override fun onValidateCLicked(response: String) {
        if (response.formatAnswer() == "temoin" || response.formatAnswer() == "temoins") {
            Alerts.showSuccess(this, ::launchNext)
        } else {
            Alerts.showError(this)
        }
    }

    override fun launchNext() {
        startActivity(Intent(this, FairePartActivity::class.java))
    }
}