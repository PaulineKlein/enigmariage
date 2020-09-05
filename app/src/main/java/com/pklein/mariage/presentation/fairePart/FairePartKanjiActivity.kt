package com.pklein.mariage.presentation.fairePart

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.pklein.mariage.R
import com.pklein.mariage.presentation.BaseActivity
import kotlinx.android.synthetic.main.activity_faire_part_kanji.*

class FairePartKanjiActivity : BaseActivity() {

    private var kanji = ""
    private var name = ""
    private var prononciation = ""
    private var signification = ""
    private var symbole = ""
    private var image: Drawable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faire_part_kanji)

        if (intent.hasExtra(KANJI_EXTRA)) {
            kanji = intent.getStringExtra(KANJI_EXTRA) ?: ""
        }

        if (kanji == KANJI.KANJI_PAULINE.name) {
            name = getString(R.string.pauline)
            prononciation = getString(R.string.faire_part_kanji_prononciation_p)
            signification = getString(R.string.faire_part_kanji_signification_p)
            symbole = getString(R.string.faire_part_kanji_symbole_p)
            image = ContextCompat.getDrawable(this, R.drawable.kanji_pauline)
        } else {
            name = getString(R.string.adrien)
            prononciation = getString(R.string.faire_part_kanji_prononciation_a)
            signification = getString(R.string.faire_part_kanji_signification_a)
            symbole = getString(R.string.faire_part_kanji_symbole_a)
            image = ContextCompat.getDrawable(this, R.drawable.kanji_adrien)
        }

        initDescription()
        iv_back?.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.neutral, R.anim.slide_out)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.neutral, R.anim.slide_out)
    }

    private fun initDescription() {
        tv_kanji_title?.text = getString(R.string.faire_part_kanji_title, name)
        tv_signification_answer?.text = signification
        tv_prononciation_answer?.text = prononciation
        tv_symbole_answer?.text = symbole
        iv_kanji.setImageDrawable(image)
    }
}