package com.epicqueststudios.cocktails.presentation.views

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.epicqueststudios.cocktails.presentation.viewmodels.CocktailViewModel

@Composable
fun MainScreenNavigation(viewModel: CocktailViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "list"
    ) {
        composable("list") {
            CocktailListScreen(viewModel, navController)
        }
        composable(route = "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType }))
        { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val id = arguments.getString("id")
            CocktailDetailScreen(viewModel, navController, id!!)
        }
    }
}