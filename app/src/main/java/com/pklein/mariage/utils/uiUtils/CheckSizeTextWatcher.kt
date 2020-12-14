package com.pklein.mariage.utils.uiUtils

import android.text.Editable
import android.text.TextWatcher

class CheckSizeTextWatcher(
    private val size: Int
) : TextWatcher {

    private var current = ""

    override fun afterTextChanged(s: Editable) {
        if (s.toString() != current) {
            if (s.toString().length <= size) {
                current = s.toString()
            }
            s.replace(0, s.length, current, 0, current.length)
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // Not used
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        // Not used
    }
}