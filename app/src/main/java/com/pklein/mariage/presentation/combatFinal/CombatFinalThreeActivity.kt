package com.pklein.mariage.presentation.combatFinal

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.presentation.resultat.ResultsActivity
import kotlinx.android.synthetic.main.activity_combat_final_three.*

class CombatFinalThreeActivity : AppCompatActivity() {

    private var nbRepeat = 1
    private val animationListener = object : Animator.AnimatorListener {
        override fun onAnimationEnd(animation: Animator?) {
            if (nbRepeat < 2) {
                animation_ko_lottie?.playAnimation()
                nbRepeat += 1
            } else {
                animation_ko_lottie?.visibility = View.GONE
                animation_ko_image?.visibility = View.VISIBLE
                button_combatFinal_three?.isEnabled = true
            }
        }

        override fun onAnimationStart(animation: Animator?) {
            // No need to implement onAnimationStart
        }

        override fun onAnimationRepeat(animation: Animator?) {
            // No need to implement onAnimationRepeat
        }

        override fun onAnimationCancel(animation: Animator?) {
            // No need to implement onAnimationCancel
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_combat_final_three)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.COMBAT_FINAL_3)

        animation_ko_lottie?.addAnimatorListener(animationListener)
        button_combatFinal_three?.setOnClickListener {
            startActivity(Intent(this, ResultsActivity::class.java))
        }
    }
}