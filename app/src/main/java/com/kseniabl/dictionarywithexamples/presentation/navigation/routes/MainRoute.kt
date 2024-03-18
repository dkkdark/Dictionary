package com.kseniabl.dictionarywithexamples.presentation.navigation.routes

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.kseniabl.dictionarywithexamples.presentation.main.MainScreen
import com.kseniabl.dictionarywithexamples.presentation.main.TopBarState
import com.kseniabl.dictionarywithexamples.presentation.navigation.Route

fun NavController.navigateToMain(route: String = Route.MainRoute.route, navOptions: NavOptions? = null) {
    this.navigate(route, navOptions)
}

fun NavGraphBuilder.mainScreen(padding: PaddingValues, toListCreationScreen: () -> Unit = {}, toWordCreation: () -> Unit = {}, topBarChanged: (TopBarState) -> Unit) {
    composable(route = Route.MainRoute.route) {
        topBarChanged(TopBarState(true, ""))
        MainScreen(padding, toListCreationScreen = { toListCreationScreen() }, toWordCreation = {
            toWordCreation()
        })
    }
}