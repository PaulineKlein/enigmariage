package com.pklein.mariage.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pklein.mariage.R
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcher
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcherListener
import kotlinx.android.synthetic.main.activity_question.*

abstract class QuestionActivity : AppCompatActivity(), CheckEmptyTextWatcherListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        et_question_answer?.addTextChangedListener(CheckEmptyTextWatcher(this))
        button_question?.setOnClickListener {
            onValidateCLicked(et_question_answer?.text.toString())
        }
    }

    abstract fun onValidateCLicked(response: String)
    abstract fun launchNext()

    override fun onTextEmpty() {
        button_question?.isEnabled = false
    }

    override fun onTextNotEmpty() {
        button_question?.isEnabled = true
    }
}