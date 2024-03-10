package com.kseniabl.dictionarywithexamples.presentation.navigation.routes

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.kseniabl.dictionarywithexamples.presentation.main.MainScreen
import com.kseniabl.dictionarywithexamples.presentation.navigation.Route
import com.kseniabl.dictionarywithexamples.presentation.navigation.WordCreationScreen
import com.kseniabl.dictionarywithexamples.presentation.word_creaton.CreateWord

fun NavController.navigateToWordCreation(route: String = WordCreationScreen, navOptions: NavOptions? = null) {
    this.navigate(route, navOptions)
}

fun NavGraphBuilder.wordCreation(padding: PaddingValues) {
    composable(route = WordCreationScreen) {
        CreateWord(padding)
    }
}