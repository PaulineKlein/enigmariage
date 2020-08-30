package com.pklein.mariage.presentation.salleDiner.cambodge

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

class SalleDinerCambodgeFourActivity : QuestionActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.SALLE_DINER_CAMBODGE_4)
        tv_question_titre?.text = getString(R.string.diner_one_titre)
        tv_question_message?.text = getString(R.string.diner_cambodge_four_question)

        animationQuestion_lottie?.visibility = View.GONE
        animationQuestion_image?.visibility = View.VISIBLE
        animationQuestion_image?.setImageResource(R.drawable.image_parchemin_mangue)

        layout_indice?.visibility = View.VISIBLE
        layout_indice?.setOnClickListener {
            Alerts.showClue(this, getString(R.string.diner_cambodge_four_indice))
        }
    }

    override fun onValidateCLicked(response: String) {
        if (response.formatAnswer() == "mangue") {
            Alerts.showSuccess(this, ::launchNext)
        } else {
            Alerts.showError(this)
        }
    }

    override fun launchNext() {
        startActivity(Intent(this, SalleDinerCambodgeFiveActivity::class.java))
    }
}