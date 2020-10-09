package com.pklein.mariage.presentation.combatFinal

import android.content.Intent
import android.os.Bundle
import com.pklein.mariage.R
import com.pklein.mariage.data.PLAYER_GENDER
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.utils.SHARED_PREFERENCE_KEY
import com.pklein.mariage.utils.SharedPreferenceStored
import com.pklein.mariage.utils.currentTimeToString
import com.pklein.mariage.utils.uiUtils.Alerts
import com.pklein.mariage.utils.uiUtils.PopinType
import kotlinx.android.synthetic.main.activity_combat_final_two.*

class CombatFinalTwoActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_combat_final_two)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.COMBAT_FINAL_2)

        setMazeImage()
        button_combatFinal_reponse?.setOnClickListener {
            val kanjiId = radio_group_coups.checkedRadioButtonId
            if (kanjiId == R.id.radioReponse2) {
                Alerts.showPopup(
                    this,
                    getString(R.string.combatFinal_sucess),
                    ::launchNext,
                    PopinType.FINAL_SUCCESS
                )
            } else {
                Alerts.showError(this)
            }
        }
    }

    private fun setMazeImage() {
        if (PlayerViewModel.getGender() == PLAYER_GENDER.FEMALE.name)
            iv_combatFinal_maze?.setImageResource(R.drawable.image_maze_final_zelda)
        else
            iv_combatFinal_maze?.setImageResource(R.drawable.image_maze_final_link)
    }

    private fun launchNext() {
        SharedPreferenceStored.storeValue(
            SHARED_PREFERENCE_KEY.PLAYER_END_TIME,
            currentTimeToString()
        )
        startActivity(Intent(this, CombatFinalThreeActivity::class.java))
    }
}