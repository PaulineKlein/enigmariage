package com.pklein.mariage.utils.uiUtils

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import com.pklein.mariage.R
import kotlinx.android.synthetic.main.cadena.view.*

interface CadenaLayoutListener {
    fun onValidateCLicked(response: String)
}

class CadenaLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr), CheckEmptyTextWatcherListener {
    private var arrayButtons: Array<AppCompatButton>
    var listener : CadenaLayoutListener? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.cadena, this, true)

        if(context is CadenaLayoutListener){
            listener = context
        }

        arrayButtons = arrayOf(
            button_un,
            button_deux,
            button_trois,
            button_quatre,
            button_cinq,
            button_six,
            button_sept,
            button_huit,
            button_neuf,
            button_zero
        )

        initEditText()
        initButton()
    }

    private fun initEditText() {
        et_cadena_value?.apply {
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(windowToken, 0)
        }
        et_cadena_value.addTextChangedListener(CheckEmptyTextWatcher(this))
    }

    private fun initButton() {
        button_back.setOnClickListener {
            et_cadena_value.setText(getEditTextValue().dropLast(1))
        }

        for (button in arrayButtons) {
            button.setOnClickListener {
                et_cadena_value.setText(getEditTextValue() + button.text)
            }
        }
    }

    fun getEditTextValue(): String {
        return et_cadena_value?.text.toString()
    }

    override fun onTextEmpty() {
        button_check.setImageDrawable(getDrawable(context,R.drawable.image_check_disabled))
        button_check.isClickable = false
        button_check.isFocusable = false
    }

    override fun onTextNotEmpty() {
        button_check.setImageDrawable(getDrawable(context,R.drawable.image_check))
        button_check.isClickable = true
        button_check.isFocusable = true
        button_check.setOnClickListener{
            listener?.onValidateCLicked(getEditTextValue())
        }
    }
}