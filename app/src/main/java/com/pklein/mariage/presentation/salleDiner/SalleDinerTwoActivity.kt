package com.pklein.mariage.presentation.salleDiner

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.data.UniversViewModel
import com.pklein.mariage.presentation.CarnetBord2Activity
import com.pklein.mariage.presentation.CarnetBordActivity
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.presentation.QuestionActivity
import com.pklein.mariage.utils.SHARED_PREFERENCE_KEY
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts
import com.pklein.mariage.utils.uiUtils.PopinType

class SalleDinerTwoActivity : QuestionActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.SALLE_DINER_2)
        binding.tvQuestionTitre.text = getString(R.string.diner_one_titre)
        binding.tvQuestionMessage.text = getText(R.string.diner_two_message)
        UniversViewModel.storeUniversPage(
            SHARED_PREFERENCE_KEY.UNIVERS_2_COCKTAILS,
            LAST_ACTIVITY_LAUNCH.SALLE_DINER_2
        )
        binding.ivHome.setOnClickListener {
            startActivity(Intent(this, CarnetBordActivity::class.java))
        }
        binding.animationQuestionLottie.visibility = View.GONE
        binding.animationQuestionImage.visibility = View.VISIBLE
        binding.animationQuestionImage.setImageResource(R.drawable.image_jo_flag)
    }

    override fun onValidateCLicked(response: String) {
        if (response.formatAnswer() == "canada") {
            Alerts.showPopup(
                this,
                getString(R.string.diner_two_bravo),
                ::launchNext,
                PopinType.CLAPPING
            )
        } else {
            Alerts.showError(this)
        }
    }

    override fun launchNext() {
        UniversViewModel.finishUnivers(SHARED_PREFERENCE_KEY.UNIVERS_2_COCKTAILS)
        startActivity(Intent(this, CarnetBord2Activity::class.java))
    }
}