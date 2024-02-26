package com.kseniabl.dictionarywithexamples.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kseniabl.dictionarywithexamples.presentation.main.MainScreen

@Composable
fun DictionaryNavHost(
    navController: NavHostController,
    padding: PaddingValues
) {
    NavHost(navController, startDestination = Route.MainRoute.route) {
        composable(Route.MainRoute.route) {
            MainScreen(padding)
        }
    }
}