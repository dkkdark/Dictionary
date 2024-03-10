package com.kseniabl.dictionarywithexamples.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.kseniabl.dictionarywithexamples.R

sealed class Route(val route: String, @StringRes val resourceId: Int, @DrawableRes val icon: Int) {
    object MainRoute: Route("main_route", R.string.main, R.drawable.world)

}

val ListCreationScreen = "list_creation_screen"
val WordCreationScreen = "word_creation_screen"