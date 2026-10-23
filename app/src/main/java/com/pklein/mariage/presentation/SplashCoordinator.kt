package com.pklein.mariage.presentation

import android.content.Context
import android.content.Intent
import com.pklein.mariage.presentation.carnetBord.CarnetBordActivity
import com.pklein.mariage.presentation.carnetBord.CarnetBordUniversActivity
import com.pklein.mariage.presentation.combatFinal.CombatFinalActivity
import com.pklein.mariage.presentation.destinations.cambodge.SalleDinerCambodgeActivity
import com.pklein.mariage.presentation.destinations.canada.SalleDinerCanadaActivity
import com.pklein.mariage.presentation.destinations.caraibes.SalleDinerCaraibesActivity
import com.pklein.mariage.presentation.destinations.italie.SalleDinerItalieActivity
import com.pklein.mariage.presentation.destinations.japon.SalleDinerJaponActivity
import com.pklein.mariage.presentation.destinations.tanzanie.SalleDinerTanzanieActivity
import com.pklein.mariage.presentation.introduction.AddPlayerActivity
import com.pklein.mariage.presentation.introduction.IntroductionActivity
import com.pklein.mariage.presentation.localisationBateau.LocalisationBateauActivity
import com.pklein.mariage.presentation.salleCocktails.SalleCocktailsActivity
import com.pklein.mariage.presentation.sallePhotobooth.SallePhotoboothActivity
import com.pklein.mariage.presentation.salleReception.ReceptionActivity

sealed class GameStep(
    val activityClass: Class<*>,
    val key: String,
) {
    // Keeps backward compat: existing .name usages in activities still return the right SharedPreferences string
    val name: String get() = key

    object Introduction : GameStep(IntroductionActivity::class.java, "INTRODUCTION")

    object AddPlayer : GameStep(AddPlayerActivity::class.java, "ADD_PLAYER")

    object LocalisationBateau1 :
        GameStep(LocalisationBateauActivity::class.java, "LOCALISATION_BATEAU_1")

    object LocalisationBateau2 :
        GameStep(LocalisationBateauActivity::class.java, "LOCALISATION_BATEAU_3")

    object SalleEmbarquement1 :
        GameStep(LocalisationBateauActivity::class.java, "SALLE_EMBARQUEMENT_1")

    object SalleEmbarquement2 :
        GameStep(LocalisationBateauActivity::class.java, "SALLE_EMBARQUEMENT_2")

    object SalleEmbarquement3 :
        GameStep(LocalisationBateauActivity::class.java, "SALLE_EMBARQUEMENT_3")

    object SalleCocktail1 : GameStep(SalleCocktailsActivity::class.java, "SALLE_COCKTAIL_1")

    object SalleCocktail2 : GameStep(SalleCocktailsActivity::class.java, "SALLE_COCKTAIL_2")

    object SalleCocktail3 : GameStep(SalleCocktailsActivity::class.java, "SALLE_COCKTAIL_3")

    object SalleCocktail4 : GameStep(SalleCocktailsActivity::class.java, "SALLE_COCKTAIL_4")

    object SalleCocktail5 : GameStep(SalleCocktailsActivity::class.java, "SALLE_COCKTAIL_5")

    object SalleCocktail6 : GameStep(SalleCocktailsActivity::class.java, "SALLE_COCKTAIL_6")

    object SalleDiner1 : GameStep(SalleCocktailsActivity::class.java, "SALLE_DINER_1")

    object SalleDiner2 : GameStep(SalleCocktailsActivity::class.java, "SALLE_DINER_2")

    object Reception1 : GameStep(ReceptionActivity::class.java, "RECEPTION_1")

    object Reception2 : GameStep(ReceptionActivity::class.java, "RECEPTION_2")

    object Reception3 : GameStep(ReceptionActivity::class.java, "RECEPTION_3")

    object Reception4 : GameStep(ReceptionActivity::class.java, "RECEPTION_4")

    object Reception5 : GameStep(ReceptionActivity::class.java, "RECEPTION_5")

    object Reception6 : GameStep(ReceptionActivity::class.java, "RECEPTION_6")

    object Reception7 : GameStep(ReceptionActivity::class.java, "RECEPTION_7")

    object Reception8 : GameStep(ReceptionActivity::class.java, "RECEPTION_8")

    object SalleDinerCanada1 :
        GameStep(SalleDinerCanadaActivity::class.java, "SALLE_DINER_CANADA_1")

    object SalleDinerCanada2 :
        GameStep(SalleDinerCanadaActivity::class.java, "SALLE_DINER_CANADA_2")

    object SalleDinerCanada3 :
        GameStep(SalleDinerCanadaActivity::class.java, "SALLE_DINER_CANADA_3")

    object SalleDinerCanada4 :
        GameStep(SalleDinerCanadaActivity::class.java, "SALLE_DINER_CANADA_4")

    object SalleDinerCanada5 :
        GameStep(SalleDinerCanadaActivity::class.java, "SALLE_DINER_CANADA_5")

    object SalleDinerCanada6 :
        GameStep(SalleDinerCanadaActivity::class.java, "SALLE_DINER_CANADA_6")

    object SalleDinerJapon1 :
        GameStep(SalleDinerJaponActivity::class.java, "SALLE_DINER_JAPON_1")

    object SalleDinerJapon2 :
        GameStep(SalleDinerJaponActivity::class.java, "SALLE_DINER_JAPON_2")

    object SalleDinerJapon3 :
        GameStep(SalleDinerJaponActivity::class.java, "SALLE_DINER_JAPON_3")

    object SalleDinerJapon4 :
        GameStep(SalleDinerJaponActivity::class.java, "SALLE_DINER_JAPON_4")

    object SalleDinerJapon5 :
        GameStep(SalleDinerJaponActivity::class.java, "SALLE_DINER_JAPON_5")

    object SalleDinerJapon6 :
        GameStep(SalleDinerJaponActivity::class.java, "SALLE_DINER_JAPON_6")

    object SalleDinerCambodge1 :
        GameStep(SalleDinerCambodgeActivity::class.java, "SALLE_DINER_CAMBODGE_1")

    object SalleDinerCambodge2 :
        GameStep(SalleDinerCambodgeActivity::class.java, "SALLE_DINER_CAMBODGE_2")

    object SalleDinerCambodge3 :
        GameStep(SalleDinerCambodgeActivity::class.java, "SALLE_DINER_CAMBODGE_3")

    object SalleDinerCambodge4 :
        GameStep(SalleDinerCambodgeActivity::class.java, "SALLE_DINER_CAMBODGE_4")

    object SalleDinerCambodge5 :
        GameStep(SalleDinerCambodgeActivity::class.java, "SALLE_DINER_CAMBODGE_5")

    object SalleDinerCaraibes1 :
        GameStep(SalleDinerCaraibesActivity::class.java, "SALLE_DINER_CARAIBES_1")

    object SalleDinerCaraibes2 :
        GameStep(SalleDinerCaraibesActivity::class.java, "SALLE_DINER_CARAIBES_2")

    object SalleDinerCaraibes3 :
        GameStep(SalleDinerCaraibesActivity::class.java, "SALLE_DINER_CARAIBES_3")

    object SalleDinerCaraibes4 :
        GameStep(SalleDinerCaraibesActivity::class.java, "SALLE_DINER_CARAIBES_4")

    object SalleDinerCaraibes5 :
        GameStep(SalleDinerCaraibesActivity::class.java, "SALLE_DINER_CARAIBES_5")

    object SalleDinerCaraibes6 :
        GameStep(SalleDinerCaraibesActivity::class.java, "SALLE_DINER_CARAIBES_6")

    object SalleDinerItalie1 :
        GameStep(SalleDinerItalieActivity::class.java, "SALLE_DINER_ITALIE_1")

    object SalleDinerItalie2 :
        GameStep(SalleDinerItalieActivity::class.java, "SALLE_DINER_ITALIE_2")

    object SalleDinerItalie3 :
        GameStep(SalleDinerItalieActivity::class.java, "SALLE_DINER_ITALIE_3")

    object SalleDinerItalie4 :
        GameStep(SalleDinerItalieActivity::class.java, "SALLE_DINER_ITALIE_4")

    object SalleDinerItalie5 :
        GameStep(SalleDinerItalieActivity::class.java, "SALLE_DINER_ITALIE_5")

    object SalleDinerItalie6 :
        GameStep(SalleDinerItalieActivity::class.java, "SALLE_DINER_ITALIE_6")

    object SalleDinerItalie7 :
        GameStep(SalleDinerItalieActivity::class.java, "SALLE_DINER_ITALIE_7")

    object SalleDinerTanzanie1 :
        GameStep(SalleDinerTanzanieActivity::class.java, "SALLE_DINER_TANZANIE_1")

    object SalleDinerTanzanie2 :
        GameStep(SalleDinerTanzanieActivity::class.java, "SALLE_DINER_TANZANIE_2")

    object SalleDinerTanzanie3 :
        GameStep(SalleDinerTanzanieActivity::class.java, "SALLE_DINER_TANZANIE_3")

    object SalleDinerTanzanie4 :
        GameStep(SalleDinerTanzanieActivity::class.java, "SALLE_DINER_TANZANIE_4")

    object SalleDinerTanzanie5 :
        GameStep(SalleDinerTanzanieActivity::class.java, "SALLE_DINER_TANZANIE_5")

    object SalleDinerTanzanie6 :
        GameStep(SalleDinerTanzanieActivity::class.java, "SALLE_DINER_TANZANIE_6")

    object SalleDinerTanzanie7 :
        GameStep(SalleDinerTanzanieActivity::class.java, "SALLE_DINER_TANZANIE_7")

    object SallePhotobooth1 :
        GameStep(SallePhotoboothActivity::class.java, "SALLE_PHOTOBOOTH_1")

    object SallePhotobooth2 : GameStep(SallePhotoboothActivity::class.java, "SALLE_PHOTOBOOTH_2")

    object SallePhotobooth3 : GameStep(SallePhotoboothActivity::class.java, "SALLE_PHOTOBOOTH_3")

    object CombatFinal1 : GameStep(CombatFinalActivity::class.java, "COMBAT_FINAL_1")

    object CombatFinal2Rabbit : GameStep(CombatFinalActivity::class.java, "COMBAT_FINAL_2_RABBIT")

    object CombatFinal2Dog : GameStep(CombatFinalActivity::class.java, "COMBAT_FINAL_2_DOG")

    object CombatFinal3 : GameStep(CombatFinalActivity::class.java, "COMBAT_FINAL_3")

    object Result : GameStep(CombatFinalActivity::class.java, "RESULT")

    object CarnetBord : GameStep(CarnetBordActivity::class.java, "CARNET_BORD")

    object CarnetBord2 : GameStep(CarnetBordUniversActivity::class.java, "CARNET_BORD2")

    companion object {
        private val byKey: Map<String, GameStep> by lazy {
            GameStep::class
                .sealedSubclasses
                .mapNotNull { it.objectInstance }
                .associateBy { it.key }
        }

        fun fromKey(key: String): GameStep = byKey[key] ?: Introduction
    }
}

object SplashCoordinator {
    const val EXTRA_STEP = "extra_step"

    fun launchGame(
        step: String,
        context: Context,
    ) {
        val gameStep = GameStep.fromKey(step)
        val intent =
            Intent(context, gameStep.activityClass).apply {
                putExtra(EXTRA_STEP, step)
            }
        context.startActivity(intent, null)
    }
}
