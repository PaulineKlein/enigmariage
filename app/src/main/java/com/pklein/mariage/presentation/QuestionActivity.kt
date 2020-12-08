package com.pklein.mariage.presentation

import android.os.Bundle
import com.pklein.mariage.databinding.ActivityQuestionBinding
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcher
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcherListener

abstract class QuestionActivity : BaseActivity(), CheckEmptyTextWatcherListener {

    lateinit var binding: ActivityQuestionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.etQuestionAnswer.addTextChangedListener(CheckEmptyTextWatcher(this))
        binding.buttonQuestion.setOnClickListener {
            onValidateCLicked(binding.etQuestionAnswer.text.toString())
        }
    }

    abstract fun onValidateCLicked(response: String)
    abstract fun launchNext()

    override fun onTextEmpty() {
        binding.buttonQuestion.isEnabled = false
    }

    override fun onTextNotEmpty() {
        binding.buttonQuestion.isEnabled = true
    }
}