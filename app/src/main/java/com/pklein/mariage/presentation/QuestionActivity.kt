package com.pklein.mariage.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
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
        binding.ivHome.setOnClickListener {
            startActivity(Intent(this, CarnetBordActivity::class.java))
        }
    }

    abstract fun onValidateCLicked(response: String)
    abstract fun launchNext()

    fun hideHomeButton() {
        binding.ivHome.visibility = View.GONE
    }

    override fun onTextEmpty() {
        binding.buttonQuestion.isEnabled = false
    }

    override fun onTextNotEmpty() {
        binding.buttonQuestion.isEnabled = true
    }
}