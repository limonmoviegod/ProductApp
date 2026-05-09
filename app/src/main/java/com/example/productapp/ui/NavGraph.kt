package com.example.productapp.presentation.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            ProductListScreen(
                onSearchClick = { navController.navigate("search") }
            )
        }
        composable("search") {
            SearchScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}