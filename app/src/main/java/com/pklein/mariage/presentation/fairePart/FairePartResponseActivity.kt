package com.pklein.mariage.presentation.fairePart

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.data.UniversViewModel
import com.pklein.mariage.databinding.ActivityFairePartResponseBinding
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.CarnetBordActivity
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.presentation.salleEmbarquement.SalleEmbarquementOneActivity
import com.pklein.mariage.utils.SHARED_PREFERENCE_KEY

const val KANJI_EXTRA = "kanji"

enum class KANJI {
    KANJI_PAULINE,
    KANJI_ADRIEN
}

class FairePartResponseActivity : BaseActivity() {

    private lateinit var binding: ActivityFairePartResponseBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFairePartResponseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.FAIRE_PART_RESPONSE)
        UniversViewModel.storeUniversPage(
            SHARED_PREFERENCE_KEY.UNIVERS_1,
            LAST_ACTIVITY_LAUNCH.FAIRE_PART_RESPONSE
        )
        binding.ivHome.setOnClickListener {
            startActivity(Intent(this, CarnetBordActivity::class.java))
        }

        binding.ivKanjiPauline.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                view.performClick()
                Handler().postDelayed({
                    val intent = Intent(this, FairePartKanjiActivity::class.java)
                    intent.putExtra(KANJI_EXTRA, KANJI.KANJI_PAULINE.name)
                    startActivity(intent)
                }, 1000)
            }
            true
        }

        binding.ivKanjiAdrien.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                view.performClick()
                Handler().postDelayed({
                    val intent = Intent(this, FairePartKanjiActivity::class.java)
                    intent.putExtra(KANJI_EXTRA, KANJI.KANJI_ADRIEN.name)
                    startActivity(intent)
                }, 1000)
            }
            true
        }

        binding.buttonResponseFairePart.setOnClickListener {
            val intent = Intent(this, SalleEmbarquementOneActivity::class.java)
            startActivity(intent)
        }
    }
}