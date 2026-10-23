package com.pklein.mariage.data.model

import com.pklein.mariage.R
import com.pklein.mariage.data.repository.PlayerGender
import com.pklein.mariage.domain.IInspector
import com.pklein.mariage.domain.Univers2Path
import com.pklein.mariage.presentation.GameStep

class AgentCroquette : IInspector {
    override val gender: PlayerGender
        get() = PlayerGender.DOG

    override val nameId: Int
        get() = R.string.add_player_gender_dog

    override val avatarResId: Int
        get() = R.drawable.image_perso_dog

    override val universPath =
        Univers2Path(
            mainUnivers = SharedPreferenceKey.UNIVERS_2_RECEPTION,
            firstStep = GameStep.Reception1,
            subUnivers =
                listOf(
                    SharedPreferenceKey.UNIVERS_2_CARAIBES,
                    SharedPreferenceKey.UNIVERS_2_TANZANIE,
                    SharedPreferenceKey.UNIVERS_2_ITALIE,
                ),
            subUniversName =
                listOf(
                    R.string.carnet_bord2_button_caraibes,
                    R.string.carnet_bord2_button_tanzanie,
                    R.string.carnet_bord2_button_italie,
                ),
            subUniversFirstStep =
                listOf(
                    GameStep.SalleDinerCaraibes1,
                    GameStep.SalleDinerTanzanie1,
                    GameStep.SalleDinerItalie1,
                ),
            subUniversIvMapmonde =
                listOf(
                    R.id.iv_mapmonde_caraibes,
                    R.id.iv_mapmonde_tanzanie,
                    R.id.iv_mapmonde_italie,
                ),
            completeTrophy = Trophies.TROPHY_DOG_RECETTE_COMPLETE,
        )

    override fun getDescription(step: GameStep?): Int? =
        when (step) {
            GameStep.LocalisationBateau1 -> R.string.localisation_one_question_dog
            GameStep.LocalisationBateau2 -> R.string.localisation_two_question_dog
            GameStep.SalleEmbarquement1 -> R.string.embarquement_one_message_1_dog
            GameStep.SalleEmbarquement2 -> R.string.embarquement_two_message_1_dog
            GameStep.SalleEmbarquement3 -> R.string.embarquement_three_help_dog
            GameStep.SallePhotobooth3 -> R.string.photobooth_three_message_2_dog
            else -> null
        }

    override fun getEnigme(step: GameStep?): Int? =
        when (step) {
            GameStep.LocalisationBateau1 -> R.drawable.image_parchemin_mouette
            GameStep.LocalisationBateau2 -> R.drawable.image_parchemin_maire
            GameStep.SalleEmbarquement1 -> R.drawable.image_number_one_dog
            GameStep.SalleEmbarquement3 -> R.string.embarquement_three_enigme_dog
            GameStep.SallePhotobooth1 -> R.drawable.image_gourde_propre_dog
            GameStep.SallePhotobooth2 -> R.drawable.image_parchemin_cameraman
            GameStep.SallePhotobooth3 -> R.drawable.image_photographe_dog
            else -> null
        }

    override fun getEnigmeAccessibility(step: GameStep?): Int? =
        when (step) {
            GameStep.LocalisationBateau1 -> R.string.localiser_bateau_enigme_accessibility_dog
            GameStep.LocalisationBateau2 -> R.string.vol_faire_part_enigme_accessibility_dog
            GameStep.SalleEmbarquement1 -> R.string.image_enigme_accessibility
            GameStep.SallePhotobooth1 -> R.string.salle_photobooth_gourde_accessibility_dog
            GameStep.SallePhotobooth2 -> R.string.salle_photobooth_charade_accessibility_dog
            else -> null
        }

    override fun getClue(step: GameStep?): Int? =
        when (step) {
            GameStep.LocalisationBateau1 -> R.string.localisation_one_indice_dog
            GameStep.LocalisationBateau2 -> R.string.localisation_two_indice_dog
            GameStep.SalleEmbarquement3 -> R.string.embarquement_three_clue_dog
            else -> null
        }

    override fun getReponse(step: GameStep?): Int? =
        when (step) {
            GameStep.LocalisationBateau1 -> R.string.localisation_one_reponse_dog
            GameStep.LocalisationBateau2 -> R.string.localisation_two_reponse_dog
            GameStep.SalleEmbarquement1 -> R.string.embarquement_one_response_dog
            GameStep.SalleEmbarquement3 -> R.string.embarquement_three_reponse_answer_dog
            GameStep.SallePhotobooth2 -> R.string.photobooth_two_reponse_answer_dog
            GameStep.SallePhotobooth3 -> R.string.photobooth_three_reponse_answer_dog
            else -> null
        }
}
