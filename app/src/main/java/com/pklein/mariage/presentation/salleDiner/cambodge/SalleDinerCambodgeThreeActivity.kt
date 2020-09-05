package com.pklein.mariage.presentation.salleDiner.cambodge

import android.content.Intent
import android.os.Bundle
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.utils.uiUtils.Alerts
import kotlinx.android.synthetic.main.activity_salle_diner_cambodge_three.*

class SalleDinerCambodgeThreeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salle_diner_cambodge_three)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.SALLE_DINER_CAMBODGE_3)

        button_diner_cambodge_three?.setOnClickListener {
            val kanjiId = radio_group_kanji.checkedRadioButtonId
            if (kanjiId == R.id.radioReponse1) {
                Alerts.showSuccess(this, ::launchNext)
            } else {
                Alerts.showError(this)
            }
        }
    }

    private fun launchNext() {
        startActivity(Intent(this, SalleDinerCambodgeFourActivity::class.java))
    }
}