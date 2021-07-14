package com.pklein.mariage.presentation.introduction

import android.content.Intent
import android.os.Bundle
import com.pklein.mariage.R
import com.pklein.mariage.data.PLAYER_GENDER
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.databinding.ActivityAddPlayerBinding
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.CarnetBordActivity
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.utils.uiUtils.*

class AddPlayerActivity : BaseActivity(), CheckEmptyTextWatcherListener {

    private lateinit var binding: ActivityAddPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.ADD_PLAYER)

        binding.etAddPlayerName.addTextChangedListener(CheckEmptyTextWatcher(this))
        binding.etAddPlayerName.addTextChangedListener(CheckSizeTextWatcher(10))
        binding.buttonPlayer.setOnClickListener {
            storeInformation()
            showPopup()
        }
    }

    private fun storeInformation() {
        val genderId = binding.radioGroupGender.checkedRadioButtonId
        val gender =
            if (genderId == R.id.radioMen) {
                PLAYER_GENDER.MALE
            } else {
                PLAYER_GENDER.FEMALE
            }

        PlayerViewModel.storeGender(gender)
        PlayerViewModel.storeName(binding.etAddPlayerName.text.toString())
    }

    private fun showPopup() {
        Alerts.showPopup(
            this,
            getString(R.string.add_player_popup, binding.etAddPlayerName.text.toString()),
            ::launchNext,
            PopinType.CLAPPING
        )
    }

    private fun launchNext() {
        startActivity(Intent(this, CarnetBordActivity::class.java))
    }

    override fun onTextEmpty() {
        binding.buttonPlayer.isEnabled = false
    }

    override fun onTextNotEmpty() {
        binding.buttonPlayer.isEnabled = true
    }
}
