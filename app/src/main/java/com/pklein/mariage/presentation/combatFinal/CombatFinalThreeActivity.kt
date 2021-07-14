package com.pklein.mariage.presentation.combatFinal

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.data.UniversViewModel
import com.pklein.mariage.databinding.ActivityCombatFinalThreeBinding
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.CarnetBordActivity
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.presentation.resultat.ResultsActivity
import com.pklein.mariage.utils.SHARED_PREFERENCE_KEY

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
        UniversViewModel.storeUniversPage(
            SHARED_PREFERENCE_KEY.UNIVERS_3,
            LAST_ACTIVITY_LAUNCH.COMBAT_FINAL_3
        )
        binding.ivHome.setOnClickListener {
            startActivity(Intent(this, CarnetBordActivity::class.java))
        }

        stopCountDown()
        binding.animationKoLottie.addAnimatorListener(animationListener)
        binding.buttonCombatFinalThree.setOnClickListener {
            UniversViewModel.finishUnivers(SHARED_PREFERENCE_KEY.UNIVERS_3)
            startActivity(Intent(this, ResultsActivity::class.java))
        }
    }
}