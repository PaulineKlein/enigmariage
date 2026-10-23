package com.pklein.mariage.presentation

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.os.Build
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.pklein.mariage.R
import com.pklein.mariage.data.model.SharedPreferenceKey
import com.pklein.mariage.data.SharedPreferenceStored
import com.pklein.mariage.data.SharedPreferenceStored.storeValue
import com.pklein.mariage.data.repository.ScoreRepository
import com.pklein.mariage.utils.CountDown
import com.pklein.mariage.utils.uiUtils.Alerts
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ActivityComponent

private const val COUNTDOWN_MAX_VALUE = 11

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, R.anim.slide_in, R.anim.neutral)
        } else {
            @Suppress("DEPRECATION")
            overridePendingTransition(R.anim.slide_in, R.anim.neutral)
        }

        CountDown.updateNbOfCountDown.observe(this, Observer {
            it.getContentIfNotHandled()?.let { nb ->
                it.hasBeenHandled = true
                Alerts.showCountDown(this, nb) {
                    updateCountDown()
                }
            }
        })
    }

    private fun updateCountDown() {
        val actualNbOfCountDown = SharedPreferenceStored.getValue(SharedPreferenceKey.COUNT_DOWN)
        val newNbOfCountDown = if (actualNbOfCountDown.isNullOrEmpty()) {
            1
        } else {
            actualNbOfCountDown.toInt() + 1
        }
        storeValue(SharedPreferenceKey.COUNT_DOWN, newNbOfCountDown.toString())
    }

    protected fun launchCountDown() {
        val actualNbOfCountDown =
            SharedPreferenceStored.getValue(SharedPreferenceKey.COUNT_DOWN)

        if (
            actualNbOfCountDown.isNullOrEmpty() ||
            (actualNbOfCountDown.isNotEmpty() && actualNbOfCountDown.toInt() < COUNTDOWN_MAX_VALUE)
        ) {
            CountDown.start()
        }
    }

    fun stopCountDown() {
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
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
}