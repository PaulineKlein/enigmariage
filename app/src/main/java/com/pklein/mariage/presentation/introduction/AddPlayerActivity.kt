package com.pklein.mariage.presentation.introduction

import android.content.Intent
import android.os.Bundle
import com.pklein.mariage.R
import com.pklein.mariage.data.PLAYER_GENDER
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.presentation.localisationBateau.LocalisationBateauOneActivity
import com.pklein.mariage.utils.uiUtils.Alerts
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcher
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcherListener
import com.pklein.mariage.utils.uiUtils.PopinType
import kotlinx.android.synthetic.main.activity_add_player.*

class AddPlayerActivity : BaseActivity(), CheckEmptyTextWatcherListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_player)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.ADD_PLAYER)

        et_add_player_name?.addTextChangedListener(CheckEmptyTextWatcher(this))
        button_player?.setOnClickListener {
            storeInformation()
            showPopup()
        }
    }

    private fun storeInformation() {
        val genderId = radio_group_gender.checkedRadioButtonId
        val gender =
            if (genderId == R.id.radioMen) {
                PLAYER_GENDER.MALE
            } else {
                PLAYER_GENDER.FEMALE
            }

        PlayerViewModel.storeGender(gender)
        PlayerViewModel.storeName(et_add_player_name?.text.toString())
    }

    private fun showPopup() {
        Alerts.showPopup(
            this,
            getString(R.string.add_player_popup, et_add_player_name?.text.toString()),
            ::launchNext,
            PopinType.CLAPPING
        )
    }

    private fun launchNext() {
        startActivity(Intent(this, LocalisationBateauOneActivity::class.java))
    }

    override fun onTextEmpty() {
        button_player?.isEnabled = false
    }

    override fun onTextNotEmpty() {
        button_player?.isEnabled = true
    }
}
