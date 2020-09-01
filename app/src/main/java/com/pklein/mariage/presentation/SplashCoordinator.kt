package com.pklein.mariage.presentation

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.pklein.mariage.presentation.combatFinal.CombatFinalOneActivity
import com.pklein.mariage.presentation.combatFinal.CombatFinalThreeActivity
import com.pklein.mariage.presentation.combatFinal.CombatFinalTwoActivity
import com.pklein.mariage.presentation.fairePart.FairePartActivity
import com.pklein.mariage.presentation.fairePart.FairePartResponseActivity
import com.pklein.mariage.presentation.introduction.AddPlayerActivity
import com.pklein.mariage.presentation.introduction.IntroductionActivity
import com.pklein.mariage.presentation.introduction.PasswordActivity
import com.pklein.mariage.presentation.salleCoktails.*
import com.pklein.mariage.presentation.salleDiner.SalleDinerOneActivity
import com.pklein.mariage.presentation.salleDiner.SalleDinerTwoActivity
import com.pklein.mariage.presentation.salleDiner.cambodge.*
import com.pklein.mariage.presentation.salleDiner.canada.*
import com.pklein.mariage.presentation.salleDiner.japon.*
import com.pklein.mariage.presentation.salleEmbarquement.SalleEmbarquementOneActivity
import com.pklein.mariage.presentation.salleEmbarquement.SalleEmbarquementThreeActivity
import com.pklein.mariage.presentation.salleEmbarquement.SalleEmbarquementTwoActivity
import com.pklein.mariage.presentation.sallePhotobooth.SallePhotoboothOneActivity
import com.pklein.mariage.presentation.sallePhotobooth.SallePhotoboothTwoActivity

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
    SALLE_DINER_1,
    SALLE_DINER_2,
    SALLE_DINER_CANADA_1,
    SALLE_DINER_CANADA_2,
    SALLE_DINER_CANADA_3,
    SALLE_DINER_CANADA_4,
    SALLE_DINER_CANADA_5,
    SALLE_DINER_CANADA_6,
    SALLE_DINER_JAPON_1,
    SALLE_DINER_JAPON_2,
    SALLE_DINER_JAPON_3,
    SALLE_DINER_JAPON_4,
    SALLE_DINER_JAPON_5,
    SALLE_DINER_JAPON_6,
    SALLE_DINER_CAMBODGE_1,
    SALLE_DINER_CAMBODGE_2,
    SALLE_DINER_CAMBODGE_3,
    SALLE_DINER_CAMBODGE_4,
    SALLE_DINER_CAMBODGE_5,
    SALLE_PHOTOBOOTH_1,
    SALLE_PHOTOBOOTH_2,
    COMBAT_FINAL_1,
    COMBAT_FINAL_2,
    COMBAT_FINAL_3
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
            LAST_ACTIVITY_LAUNCH.SALLE_DINER_2.name -> {
                Intent(context, SalleDinerTwoActivity::class.java)
            }
            LAST_ACTIVITY_LAUNCH.SALLE_DINER_CANADA_1.name -> {
                Intent(context, SalleDinerCanadaOneActivity::class.java)
            }
            LAST_ACTIVITY_LAUNCH.SALLE_DINER_CANADA_2.name -> {
                Intent(context, SalleDinerCanadaTwoActivity::class.java)
            }
            LAST_ACTIVITY_LAUNCH.SALLE_DINER_CANADA_3.name -> {
                Intent(context, SalleDinerCanadaThreeActivity::class.java)
            }
            LAST_ACTIVITY_LAUNCH.SALLE_DINER_CANADA_4.name -> {
                Intent(context, SalleDinerCanadaFourActivity::class.java)
            }
            LAST_ACTIVITY_LAUNCH.SALLE_DINER_CANADA_5.name -> {
                Intent(context, SalleDinerCanadaFiveActivity::class.java)
            }
            LAST_ACTIVITY_LAUNCH.SALLE_DINER_CANADA_6.name -> {
                Intent(context, SalleDinerCanadaSixActivity::class.java)
            }
            LAST_ACTIVITY_LAUNCH.SALLE_DINER_JAPON_1.name -> {
                Intent(context, SalleDinerJaponOneActivity::class.java)
            }
            LAST_ACTIVITY_LAUNCH.SALLE_DINER_JAPON_2.name -> {
                Intent(context, SalleDinerJaponTwoActivity::class.java)
            }
            LAST_ACTIVITY_LAUNCH.SALLE_DINER_JAPON_3.name -> {
                Intent(context, SalleDinerJaponThreeActivity::class.java)
            }
            LAST_ACTIVITY_LAUNCH.SALLE_DINER_JAPON_4.name -> {
                Intent(context, SalleDinerJaponFourActivity::class.java)
            }
            LAST_ACTIVITY_LAUNCH.SALLE_DINER_JAPON_5.name -> {
                Intent(context, SalleDinerJaponFiveActivity::class.java)
            }
            LAST_ACTIVITY_LAUNCH.SALLE_DINER_JAPON_6.name -> {
                Intent(context, SalleDinerJaponSixActivity::class.java)
            }
            LAST_ACTIVITY_LAUNCH.SALLE_DINER_CAMBODGE_1.name -> {
                Intent(context, SalleDinerCambodgeOneActivity::class.java)
            }
            LAST_ACTIVITY_LAUNCH.SALLE_DINER_CAMBODGE_2.name -> {
                Intent(context, SalleDinerCambodgeTwoActivity::class.java)
            }
            LAST_ACTIVITY_LAUNCH.SALLE_DINER_CAMBODGE_3.name -> {
                Intent(context, SalleDinerCambodgeThreeActivity::class.java)
            }
            LAST_ACTIVITY_LAUNCH.SALLE_DINER_CAMBODGE_4.name -> {
                Intent(context, SalleDinerCambodgeFourActivity::class.java)
            }
            LAST_ACTIVITY_LAUNCH.SALLE_DINER_CAMBODGE_5.name -> {
                Intent(context, SalleDinerCambodgeFiveActivity::class.java)
            }
            LAST_ACTIVITY_LAUNCH.SALLE_PHOTOBOOTH_1.name -> {
                Intent(context, SallePhotoboothOneActivity::class.java)
            }
            LAST_ACTIVITY_LAUNCH.SALLE_PHOTOBOOTH_2.name -> {
                Intent(context, SallePhotoboothTwoActivity::class.java)
            }
            LAST_ACTIVITY_LAUNCH.COMBAT_FINAL_1.name -> {
                Intent(context, CombatFinalOneActivity::class.java)
            }
            LAST_ACTIVITY_LAUNCH.COMBAT_FINAL_2.name -> {
                Intent(context, CombatFinalTwoActivity::class.java)
            }
            LAST_ACTIVITY_LAUNCH.COMBAT_FINAL_3.name -> {
                Intent(context, CombatFinalThreeActivity::class.java)
            }
            else -> {
                Intent(context, PasswordActivity::class.java)
            }
        }
        startActivity(context, activityToLaunch, null)
    }
}