package com.pklein.mariage.data.model

import com.pklein.mariage.R
import com.pklein.mariage.data.repository.PlayerGender
import com.pklein.mariage.domain.IInspector
import com.pklein.mariage.domain.Univers2Path
import com.pklein.mariage.presentation.GameStep

class CapitaineCarotte : IInspector {
    override val gender: PlayerGender
        get() = PlayerGender.RABBIT

    override val nameId: Int
        get() = R.string.add_player_gender_rabbit

    override val avatarResId: Int
        get() = R.drawable.image_perso_rabbit

    override val universPath =
        Univers2Path(
            mainUnivers = SharedPreferenceKey.UNIVERS_2_COCKTAILS,
            firstStep = GameStep.SalleCocktail1,
            subUnivers =
                listOf(
                    SharedPreferenceKey.UNIVERS_2_CANADA,
                    SharedPreferenceKey.UNIVERS_2_JAPON,
                    SharedPreferenceKey.UNIVERS_2_CAMBODGE,
                ),
            subUniversName =
                listOf(
                    R.string.carnet_bord2_button_canada,
                    R.string.carnet_bord2_button_japon,
                    R.string.carnet_bord2_button_cambodge,
                ),
            subUniversFirstStep =
                listOf(
                    GameStep.SalleDinerCanada1,
                    GameStep.SalleDinerJapon1,
                    GameStep.SalleDinerCambodge1,
                ),
            subUniversIvMapmonde =
                listOf(
                    R.id.iv_mapmonde_canada,
                    R.id.iv_mapmonde_japon,
                    R.id.iv_mapmonde_cambodge,
                ),
            completeTrophy = Trophies.TROPHY_RABBIT_RECETTE_COMPLETE,
        )

    override fun getDescription(step: GameStep?): Int? =
        when (step) {
            GameStep.LocalisationBateau1 -> R.string.localisation_one_question_rabbit
            GameStep.LocalisationBateau2 -> R.string.localisation_two_question_rabbit
            GameStep.SalleEmbarquement1 -> R.string.embarquement_one_message_1_rabbit
            GameStep.SalleEmbarquement2 -> R.string.embarquement_two_message_1_rabbit
            GameStep.SalleEmbarquement3 -> R.string.embarquement_three_help_rabbit
            GameStep.SallePhotobooth3 -> R.string.photobooth_three_message_2_rabbit
            else -> null
        }

    override fun getEnigme(step: GameStep?): Int? =
        when (step) {
            GameStep.LocalisationBateau1 -> R.drawable.image_parchemin_sirene
            GameStep.LocalisationBateau2 -> R.drawable.image_parchemin_temoin
            GameStep.SalleEmbarquement1 -> R.drawable.image_number_one_rabbit
            GameStep.SalleEmbarquement3 -> R.string.embarquement_three_enigme_rabbit
            GameStep.SallePhotobooth1 -> R.drawable.image_gourde_propre_rabbit
            GameStep.SallePhotobooth2 -> R.drawable.image_parchemin_photographe
            GameStep.SallePhotobooth3 -> R.drawable.image_photographe_rabbit
            else -> null
        }

    override fun getEnigmeAccessibility(step: GameStep?): Int? =
        when (step) {
            GameStep.LocalisationBateau1 -> R.string.localiser_bateau_enigme_accessibility_rabbit
            GameStep.LocalisationBateau2 -> R.string.vol_faire_part_enigme_accessibility_rabbit
            GameStep.SalleEmbarquement1 -> R.string.image_enigme_accessibility
            GameStep.SallePhotobooth1 -> R.string.salle_photobooth_gourde_accessibility_rabbit
            GameStep.SallePhotobooth2 -> R.string.salle_photobooth_charade_accessibility_rabbit
            else -> null
        }

    override fun getClue(step: GameStep?): Int? =
        when (step) {
            GameStep.LocalisationBateau1 -> R.string.localisation_one_indice_rabbit
            GameStep.LocalisationBateau2 -> R.string.localisation_two_indice_rabbit
            GameStep.SalleEmbarquement3 -> R.string.embarquement_three_clue_rabbit
            else -> null
        }

    override fun getReponse(step: GameStep?): Int? =
        when (step) {
            GameStep.LocalisationBateau1 -> R.string.localisation_one_reponse_rabbit
            GameStep.LocalisationBateau2 -> R.string.localisation_two_reponse_rabbit
            GameStep.SalleEmbarquement1 -> R.string.embarquement_one_response_rabbit
            GameStep.SalleEmbarquement3 -> R.string.embarquement_three_reponse_answer_rabbit
            GameStep.SallePhotobooth2 -> R.string.photobooth_two_reponse_answer_rabbit
            GameStep.SallePhotobooth3 -> R.string.photobooth_three_reponse_answer_rabbit
            else -> null
        }
}
