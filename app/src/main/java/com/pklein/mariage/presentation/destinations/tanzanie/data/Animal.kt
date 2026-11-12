package com.pklein.mariage.presentation.destinations.tanzanie.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.pklein.mariage.R

data class Animal(
    @DrawableRes val footprint: Int,
    @DrawableRes val photo: Int,
    @StringRes val swahiliName: Int,
    @StringRes val name: Int,
)

// Index d'un animal = identifiant de l'empreinte qui lui correspond
val ANIMALS =
    listOf(
        Animal(
            R.drawable.image_empreinte_lion,
            R.drawable.image_empreinte_lion_soluce,
            R.string.diner_tanzanie_two_name_swahili_lion,
            R.string.diner_tanzanie_two_name_lion,
        ),
        Animal(
            R.drawable.image_empreinte_elephant,
            R.drawable.image_empreinte_elephant_soluce,
            R.string.diner_tanzanie_two_name_swahili_elephant,
            R.string.diner_tanzanie_two_name_elephant,
        ),
        Animal(
            R.drawable.image_empreinte_babouin,
            R.drawable.image_empreinte_babouin_soluce,
            R.string.diner_tanzanie_two_name_swahili_babouin,
            R.string.diner_tanzanie_two_name_babouin,
        ),
        Animal(
            R.drawable.image_empreinte_autruche,
            R.drawable.image_empreinte_autruche_soluce,
            R.string.diner_tanzanie_two_name_swahili_autruche,
            R.string.diner_tanzanie_two_name_autruche,
        ),
        Animal(
            R.drawable.image_empreinte_okapi,
            R.drawable.image_empreinte_okapi_soluce,
            R.string.diner_tanzanie_two_name_swahili_okapi,
            R.string.diner_tanzanie_two_name_okapi,
        ),
    )
