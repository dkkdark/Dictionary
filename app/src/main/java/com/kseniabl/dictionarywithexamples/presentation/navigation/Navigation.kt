package com.kseniabl.dictionarywithexamples.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kseniabl.dictionarywithexamples.presentation.list_creation.ListCreation
import com.kseniabl.dictionarywithexamples.presentation.main.MainScreen
import com.kseniabl.dictionarywithexamples.presentation.navigation.routes.listCreationScreen
import com.kseniabl.dictionarywithexamples.presentation.navigation.routes.mainScreen
import com.kseniabl.dictionarywithexamples.presentation.navigation.routes.navigateToListCreationScreen

@Composable
fun DictionaryNavHost(
    navController: NavHostController,
    padding: PaddingValues
) {
    NavHost(navController, startDestination = Route.MainRoute.route) {
        mainScreen(
            padding,
            toListCreationScreen = {
                navController.navigateToListCreationScreen()
            }
        )
        listCreationScreen(padding)
    }
}