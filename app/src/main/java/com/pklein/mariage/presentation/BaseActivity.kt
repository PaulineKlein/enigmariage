package com.pklein.mariage.presentation

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.pklein.mariage.R
import com.pklein.mariage.utils.CountDown
import com.pklein.mariage.utils.SHARED_PREFERENCE_KEY
import com.pklein.mariage.utils.SharedPreferenceStored
import com.pklein.mariage.utils.uiUtils.Alerts

const val COUNTDOWN_MAX_VALUE = 11

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.slide_in, R.anim.neutral)

        CountDown.updateNbOfCountDown.observe(this, Observer {
            it.getContentIfNotHandled()?.let { nb ->
                it.hasBeenHandled = true
                Alerts.showCountDown(this, nb)
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.neutral, R.anim.slide_out)
    }

    protected fun launchCountDown() {
        val actualNbOfCountDown =
            SharedPreferenceStored.getValue(SHARED_PREFERENCE_KEY.COUNT_DOWN)

        if (
            actualNbOfCountDown.isNullOrEmpty() ||
            (actualNbOfCountDown.isNotEmpty() && actualNbOfCountDown.toInt() < COUNTDOWN_MAX_VALUE)
        ) {
            CountDown.start()
        }
    }

    protected fun stopCountDown() {
        CountDown.cancel()
    }

    // to dismiss keyboard when click outside EditText
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm: InputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
}