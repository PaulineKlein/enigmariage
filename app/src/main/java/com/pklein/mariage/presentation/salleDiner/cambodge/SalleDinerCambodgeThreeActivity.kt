package com.pklein.mariage.presentation.salleDiner.cambodge

import android.content.Intent
import android.os.Bundle
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.databinding.ActivitySalleDinerCambodgeThreeBinding
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.utils.uiUtils.Alerts

class SalleDinerCambodgeThreeActivity : BaseActivity() {

    private lateinit var binding: ActivitySalleDinerCambodgeThreeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySalleDinerCambodgeThreeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.SALLE_DINER_CAMBODGE_3)

        binding.buttonDinerCambodgeThree.setOnClickListener {
            val kanjiId = binding.radioGroupKanji.checkedRadioButtonId
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