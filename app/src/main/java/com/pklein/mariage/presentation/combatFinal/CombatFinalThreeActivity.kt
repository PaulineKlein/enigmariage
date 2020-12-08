package com.pklein.mariage.presentation.combatFinal

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.databinding.ActivityCombatFinalThreeBinding
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.presentation.resultat.ResultsActivity

class CombatFinalThreeActivity : BaseActivity() {

    private lateinit var binding: ActivityCombatFinalThreeBinding
    private var nbRepeat = 1
    private val animationListener = object : Animator.AnimatorListener {
        override fun onAnimationEnd(animation: Animator?) {
            if (nbRepeat < 2) {
                binding.animationKoLottie.playAnimation()
                nbRepeat += 1
            } else {
                binding.animationKoLottie.visibility = View.GONE
                binding.animationKoImage.visibility = View.VISIBLE
                binding.buttonCombatFinalThree.isEnabled = true
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
        binding = ActivityCombatFinalThreeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.COMBAT_FINAL_3)

        binding.animationKoLottie.addAnimatorListener(animationListener)
        binding.buttonCombatFinalThree.setOnClickListener {
            startActivity(Intent(this, ResultsActivity::class.java))
        }
    }
}