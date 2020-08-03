package com.pklein.mariage.utils.uiUtils

import android.text.Editable
import android.text.TextWatcher

interface CheckEmptyTextWatcherListener {
    fun onTextEmpty()
    fun onTextNotEmpty()
}

class CheckEmptyTextWatcher(
    private val listener: CheckEmptyTextWatcherListener
) : TextWatcher {

    override fun afterTextChanged(s: Editable) {

        if (s.toString().isEmpty()) {
            listener.onTextEmpty()
        } else {
            listener.onTextNotEmpty()
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // Not used
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        // Not used
    }
}