package com.pklein.mariage.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pklein.mariage.R

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.slide_in, R.anim.neutral)
    }

    override fun onBackPressed() {
        finish()
        overridePendingTransition(R.anim.neutral, R.anim.slide_out)
    }
}