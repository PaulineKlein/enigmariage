package com.pklein.mariage.presentation.fairePart

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.presentation.salleEmbarquement.SalleEmbarquementOneActivity
import kotlinx.android.synthetic.main.activity_faire_part_response.*


const val KANJI_EXTRA = "kanji"

enum class KANJI {
    KANJI_PAULINE,
    KANJI_ADRIEN
}

class FairePartResponseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faire_part_response)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.FAIRE_PART_RESPONSE)

        iv_kanji_pauline?.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                this.overridePendingTransition(R.anim.slide_in, R.anim.neutral)
                view.performClick()
                val handler = Handler()
                handler.postDelayed({
                    val intent = Intent(this, FairePartKanjiActivity::class.java)
                    intent.putExtra(KANJI_EXTRA, KANJI.KANJI_PAULINE.name)
                    startActivity(intent)
                }, 1000)
            }
            false
        }

        iv_kanji_adrien?.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                this.overridePendingTransition(R.anim.slide_in, R.anim.neutral)
                view.performClick()
                val handler = Handler()
                handler.postDelayed({
                    val intent = Intent(this, FairePartKanjiActivity::class.java)
                    intent.putExtra(KANJI_EXTRA, KANJI.KANJI_ADRIEN.name)
                    startActivity(intent)
                }, 1000)
            }
            false
        }

        button_response_faire_part?.setOnClickListener {
            val intent = Intent(this, SalleEmbarquementOneActivity::class.java)
            startActivity(intent)
        }
    }
}