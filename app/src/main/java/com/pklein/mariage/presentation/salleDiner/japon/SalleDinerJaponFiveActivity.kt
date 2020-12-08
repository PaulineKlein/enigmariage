package com.pklein.mariage.presentation.salleDiner.japon

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.presentation.QuestionActivity
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts

class SalleDinerJaponFiveActivity : QuestionActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.SALLE_DINER_JAPON_5)
        binding.tvQuestionTitre.text = getString(R.string.diner_titre_japon)
        binding.tvQuestionMessage.text = getString(R.string.diner_japon_five_question)

        binding.animationQuestionLottie.visibility = View.GONE
        binding.animationQuestionImage.visibility = View.VISIBLE
        binding.animationQuestionImage?.setImageResource(R.drawable.image_rebus_tapioca)
    }

    override fun onValidateCLicked(response: String) {
        if (response.formatAnswer() == "tapioca") {
            Alerts.showSuccess(this, ::launchNext)
        } else {
            Alerts.showError(this)
        }
    }

    override fun launchNext() {
        startActivity(Intent(this, SalleDinerJaponSixActivity::class.java))
    }
}