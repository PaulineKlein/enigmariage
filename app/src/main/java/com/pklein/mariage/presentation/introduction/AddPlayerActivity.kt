package com.pklein.mariage.presentation.introduction

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.pklein.mariage.R
import com.pklein.mariage.data.model.Trophies
import com.pklein.mariage.data.repository.PlayerGender
import com.pklein.mariage.databinding.ActivityAddPlayerBinding
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.carnetBord.CarnetBordActivity
import com.pklein.mariage.utils.extension.addSystemWindowInsetToMargin
import com.pklein.mariage.utils.uiUtils.Alerts
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcher
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcherListener
import com.pklein.mariage.utils.uiUtils.CheckSizeTextWatcher
import com.pklein.mariage.utils.uiUtils.PopinType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPlayerActivity : BaseActivity(), CheckEmptyTextWatcherListener {

    private lateinit var binding: ActivityAddPlayerBinding
    private val gameProgress: GameProgressViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        gameProgress.storePage(GameStep.AddPlayer)

        binding.tvAddPlayerClue.text = getText(R.string.indice_explication)

        binding.etAddPlayerName.addTextChangedListener(CheckEmptyTextWatcher(this))
        binding.etAddPlayerName.addTextChangedListener(CheckSizeTextWatcher(10))
        binding.ivDog.setOnClickListener {
            binding.radioDog.isChecked = true
            binding.radioRabbit.isChecked = false
        }
        binding.ivRabbit.setOnClickListener {
            binding.radioDog.isChecked = false
            binding.radioRabbit.isChecked = true
        }

        binding.buttonPlayer.setOnClickListener {
            val gender = storeInformation()
            showPopup(gender)
        }
        binding.layoutIndice.indiceRoot.isEnabled = false
        binding.root.addSystemWindowInsetToMargin()
    }

    private fun storeInformation(): PlayerGender {
        val genderId = binding.radioGroupGender.checkedRadioButtonId
        val gender = if (genderId == R.id.radioDog) {
            PlayerGender.DOG
        } else {
            PlayerGender.RABBIT
        }

        gameProgress.storePlayerGender(gender)
        gameProgress.storePlayerName(binding.etAddPlayerName.text.toString())
        return gender
    }

    // gender is passed in rather than read back from gameProgress: the ViewModel's
    // IInspector/gender is resolved once at ViewModel-creation time (before the player
    // picked a gender), so it would still reflect the default here, not the choice
    // that was just persisted above.
    private fun showPopup(gender: PlayerGender) {
        Alerts.showPopup(
            this,
            getString(R.string.add_player_popup, binding.etAddPlayerName.text.toString()),
            ::launchNext,
            PopinType.CLAPPING
        )
        val trophy = when (gender) {
            PlayerGender.DOG -> Trophies.TROPHY_DOG
            PlayerGender.RABBIT -> Trophies.TROPHY_RABBIT
        }
        if (!gameProgress.isTrophyUnlocked(trophy)) {
            gameProgress.saveTrophy(trophy)
            Alerts.showTrophyUnlocked(this, getString(trophy.message))
        }
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
