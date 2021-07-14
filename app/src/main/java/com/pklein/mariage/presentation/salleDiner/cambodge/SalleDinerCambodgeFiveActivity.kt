package com.pklein.mariage.presentation.salleDiner.cambodge

import android.content.Intent
import android.os.Bundle
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.data.UniversViewModel
import com.pklein.mariage.databinding.ActivitySalleDinerCambodgeFiveBinding
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.CarnetBord2Activity
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.utils.SHARED_PREFERENCE_KEY

class SalleDinerCambodgeFiveActivity : BaseActivity() {

    private lateinit var binding: ActivitySalleDinerCambodgeFiveBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySalleDinerCambodgeFiveBinding.inflate(layoutInflater)
        setContentView(binding.root)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.SALLE_DINER_CAMBODGE_5)
        UniversViewModel.storeUniversPage(
            SHARED_PREFERENCE_KEY.UNIVERS_2_CAMBODGE, LAST_ACTIVITY_LAUNCH.SALLE_DINER_CAMBODGE_5
        )
        binding.ivHome.setOnClickListener {
            startActivity(Intent(this, CarnetBord2Activity::class.java))
        }

        binding.buttonCambodgeFive.setOnClickListener {
            UniversViewModel.finishUnivers(SHARED_PREFERENCE_KEY.UNIVERS_2_CAMBODGE)
            startActivity(Intent(this, CarnetBord2Activity::class.java))
        }
    }
}