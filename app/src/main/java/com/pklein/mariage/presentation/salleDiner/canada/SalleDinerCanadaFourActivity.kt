package com.pklein.mariage.presentation.salleDiner.canada

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.presentation.QuestionActivity
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts

class SalleDinerCanadaFourActivity : QuestionActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.SALLE_DINER_CANADA_4)

        binding.tvQuestionTitre.text = getString(R.string.diner_titre_canada)
        binding.tvQuestionMessage.text = getString(R.string.diner_canada_four_message)
        binding.animationQuestionLottie.visibility = View.GONE
        binding.animationQuestionImage.visibility = View.VISIBLE
        binding.animationQuestionImage.setImageResource(R.drawable.image_calcul_2)
        binding.etQuestionAnswer.inputType = EditorInfo.TYPE_CLASS_NUMBER

        binding.layoutIndice.root.visibility = View.VISIBLE
        binding.layoutIndice.root.setOnClickListener {
            Alerts.showClue(this, getString(R.string.diner_canada_four_indice))
        }
    }

    override fun onValidateCLicked(response: String) {
        if (response.formatAnswer() == "32") {
            Alerts.showSuccess(this, ::launchNext)
        } else {
            Alerts.showError(this)
        }
    }

    override fun launchNext() {
        startActivity(Intent(this, SalleDinerCanadaFiveActivity::class.java))
    }
}