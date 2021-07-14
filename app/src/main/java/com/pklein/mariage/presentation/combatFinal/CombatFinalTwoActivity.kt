package com.pklein.mariage.presentation.combatFinal

import android.content.Intent
import android.os.Bundle
import com.pklein.mariage.R
import com.pklein.mariage.data.PLAYER_GENDER
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.data.UniversViewModel
import com.pklein.mariage.databinding.ActivityCombatFinalTwoBinding
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.CarnetBordActivity
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.utils.SHARED_PREFERENCE_KEY
import com.pklein.mariage.utils.SharedPreferenceStored
import com.pklein.mariage.utils.currentTimeToString
import com.pklein.mariage.utils.uiUtils.Alerts
import com.pklein.mariage.utils.uiUtils.PopinType

class CombatFinalTwoActivity : BaseActivity() {

    private lateinit var binding: ActivityCombatFinalTwoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCombatFinalTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.COMBAT_FINAL_2)
        UniversViewModel.storeUniversPage(
            SHARED_PREFERENCE_KEY.UNIVERS_3,
            LAST_ACTIVITY_LAUNCH.COMBAT_FINAL_2
        )
        binding.ivHome.setOnClickListener {
            startActivity(Intent(this, CarnetBordActivity::class.java))
        }

        setMazeImage()
        binding.buttonCombatFinalReponse.setOnClickListener {
            val kanjiId = binding.radioGroupCoups.checkedRadioButtonId
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
            binding.ivCombatFinalMaze.setImageResource(R.drawable.image_maze_final_zelda)
        else
            binding.ivCombatFinalMaze.setImageResource(R.drawable.image_maze_final_link)
    }

    private fun launchNext() {
        SharedPreferenceStored.storeValue(
            SHARED_PREFERENCE_KEY.PLAYER_END_TIME,
            currentTimeToString()
        )
        startActivity(Intent(this, CombatFinalThreeActivity::class.java))
    }
}