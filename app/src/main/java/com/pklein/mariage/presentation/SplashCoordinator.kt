package com.pklein.mariage.presentation

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.pklein.mariage.presentation.salleDiner.SalleDinerOneActivity
import com.pklein.mariage.presentation.fairePart.FairePartActivity
import com.pklein.mariage.presentation.fairePart.FairePartResponseActivity
import com.pklein.mariage.presentation.introduction.AddPlayerActivity
import com.pklein.mariage.presentation.introduction.IntroductionActivity
import com.pklein.mariage.presentation.salleCoktails.*
import com.pklein.mariage.presentation.salleEmbarquement.SalleEmbarquementOneActivity
import com.pklein.mariage.presentation.salleEmbarquement.SalleEmbarquementThreeActivity
import com.pklein.mariage.presentation.salleEmbarquement.SalleEmbarquementTwoActivity

enum class LAST_ACTIVITY_LAUNCH {
    INTRODUCTION,
    ADD_PLAYER,
    FAIRE_PART,
    FAIRE_PART_RESPONSE,
    SALLE_EMBARQUEMENT_1,
    SALLE_EMBARQUEMENT_2,
    SALLE_EMBARQUEMENT_3,
    SALLE_COKTAIL_1,
    SALLE_COKTAIL_2,
    SALLE_COKTAIL_3,
    SALLE_COKTAIL_4,
    SALLE_COKTAIL_5,
    SALLE_COKTAIL_6,
    SALLE_COKTAIL_7,
    SALLE_DINER_1
}

object SplashCoordinator {

    fun launchGame(step: String, context: Context) {
        val activityToLaunch = when (step) {
            LAST_ACTIVITY_LAUNCH.INTRODUCTION.name -> {
                Intent(context, IntroductionActivity::class.java)
            }
            LAST_ACTIVITY_LAUNCH.ADD_PLAYER.name -> {
                Intent(context, AddPlayerActivity::class.java)
            }
            LAST_ACTIVITY_LAUNCH.FAIRE_PART.name -> {
                Intent(context, FairePartActivity::class.java)
            }
            LAST_ACTIVITY_LAUNCH.FAIRE_PART_RESPONSE.name -> {
                Intent(context, FairePartResponseActivity::class.java)
            }
            LAST_ACTIVITY_LAUNCH.SALLE_EMBARQUEMENT_1.name -> {
                Intent(context, SalleEmbarquementOneActivity::class.java)
            }
            LAST_ACTIVITY_LAUNCH.SALLE_EMBARQUEMENT_2.name -> {
                Intent(context, SalleEmbarquementTwoActivity::class.java)
            }
            LAST_ACTIVITY_LAUNCH.SALLE_EMBARQUEMENT_3.name -> {
                Intent(context, SalleEmbarquementThreeActivity::class.java)
            }
            LAST_ACTIVITY_LAUNCH.SALLE_COKTAIL_1.name -> {
                Intent(context, SalleCoktailsOneActivity::class.java)
            }
            LAST_ACTIVITY_LAUNCH.SALLE_COKTAIL_2.name -> {
                Intent(context, SalleCoktailsTwoActivity::class.java)
            }
            LAST_ACTIVITY_LAUNCH.SALLE_COKTAIL_3.name -> {
                Intent(context, SalleCoktailsThreeActivity::class.java)
            }
            LAST_ACTIVITY_LAUNCH.SALLE_COKTAIL_4.name -> {
                Intent(context, SalleCoktailsFourActivity::class.java)
            }
            LAST_ACTIVITY_LAUNCH.SALLE_COKTAIL_5.name -> {
                Intent(context, SalleCoktailsFiveActivity::class.java)
            }
            LAST_ACTIVITY_LAUNCH.SALLE_COKTAIL_6.name -> {
                Intent(context, SalleCoktailsSixActivity::class.java)
            }
            LAST_ACTIVITY_LAUNCH.SALLE_COKTAIL_7.name -> {
                Intent(context, SalleCoktailsSevenActivity::class.java)
            }
            LAST_ACTIVITY_LAUNCH.SALLE_DINER_1.name -> {
                Intent(context, SalleDinerOneActivity::class.java)
            }
            else -> {
                Intent(context, IntroductionActivity::class.java)
            }
        }
        startActivity(context, activityToLaunch, null)
    }
}