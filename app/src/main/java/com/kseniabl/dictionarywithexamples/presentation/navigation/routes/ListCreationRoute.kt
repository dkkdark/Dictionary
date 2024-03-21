package com.kseniabl.dictionarywithexamples.presentation.navigation.routes

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.kseniabl.dictionarywithexamples.presentation.list_creation.ListCreation
import com.kseniabl.dictionarywithexamples.presentation.main.TopBarState
import com.kseniabl.dictionarywithexamples.presentation.navigation.ListCreationScreen

fun NavController.navigateToListCreationScreen(route: String = ListCreationScreen, navOptions: NavOptions? = null) {
    this.navigate(route, navOptions)
}

fun NavGraphBuilder.listCreationScreen(padding: PaddingValues, toMainScreen: () -> Unit, topBarChanged: (TopBarState) -> Unit) {
    composable(route = ListCreationScreen) {
        topBarChanged(TopBarState(false, "Новый список"))
        ListCreation(padding, toMainScreen)
    }
}