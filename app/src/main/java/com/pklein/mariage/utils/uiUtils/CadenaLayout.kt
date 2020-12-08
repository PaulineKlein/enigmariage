package com.pklein.mariage.utils.uiUtils

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageButton
import androidx.cardview.widget.CardView
import com.pklein.mariage.R

interface CadenaLayoutListener {
    fun onValidateCLicked(response: String)
}

class CadenaLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr), CheckEmptyTextWatcherListener {
    private var arrayButtons: Array<AppCompatButton>
    private var listener: CadenaLayoutListener? = null
    private var cadenaValue: AppCompatEditText? = null
    private var buttonCheck: AppCompatImageButton? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.cadena, this, true)
        cadenaValue = findViewById(R.id.et_cadena_value)
        buttonCheck = findViewById(R.id.button_check)

        if (context is CadenaLayoutListener) {
            listener = context
        }

        arrayButtons = arrayOf(
            findViewById(R.id.button_un),
            findViewById(R.id.button_deux),
            findViewById(R.id.button_trois),
            findViewById(R.id.button_quatre),
            findViewById(R.id.button_cinq),
            findViewById(R.id.button_six),
            findViewById(R.id.button_sept),
            findViewById(R.id.button_huit),
            findViewById(R.id.button_neuf),
            findViewById(R.id.button_zero)
        )

        initEditText()
        initButton()
    }

    private fun initEditText() {
        cadenaValue?.apply {
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(windowToken, 0)
        }
        cadenaValue?.addTextChangedListener(CheckEmptyTextWatcher(this))
    }

    private fun initButton() {
        findViewById<AppCompatImageButton>(R.id.button_back).setOnClickListener {
            cadenaValue?.setText(getEditTextValue().dropLast(1))
        }

        for (button in arrayButtons) {
            button.setOnClickListener {
                cadenaValue?.setText(getEditTextValue() + button.text)
            }
        }
    }

    private fun getEditTextValue(): String {
        return cadenaValue?.text.toString()
    }

    override fun onTextEmpty() {
        buttonCheck?.setImageDrawable(getDrawable(context, R.drawable.image_check_disabled))
        buttonCheck?.isClickable = false
        buttonCheck?.isFocusable = false
    }

    override fun onTextNotEmpty() {
        buttonCheck?.setImageDrawable(getDrawable(context, R.drawable.image_check))
        buttonCheck?.isClickable = true
        buttonCheck?.isFocusable = true
        buttonCheck?.setOnClickListener {
            listener?.onValidateCLicked(getEditTextValue())
        }
    }
}