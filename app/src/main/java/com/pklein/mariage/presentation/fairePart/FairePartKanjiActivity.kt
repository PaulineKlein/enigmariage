package com.pklein.mariage.presentation.fairePart

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.pklein.mariage.R
import com.pklein.mariage.databinding.ActivityFairePartKanjiBinding
import com.pklein.mariage.presentation.BaseActivity

class FairePartKanjiActivity : BaseActivity() {

    private lateinit var binding: ActivityFairePartKanjiBinding
    private var kanji = ""
    private var name = ""
    private var prononciation = ""
    private var signification = ""
    private var symbole = ""
    private var image: Drawable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFairePartKanjiBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        binding.ivBack.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.neutral, R.anim.slide_out)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.neutral, R.anim.slide_out)
    }

    private fun initDescription() {
        binding.tvKanjiTitle.text = getString(R.string.faire_part_kanji_title, name)
        binding.tvSignificationAnswer.text = signification
        binding.tvPrononciationAnswer.text = prononciation
        binding.tvSymboleAnswer.text = symbole
        binding.ivKanji.setImageDrawable(image)
    }
}