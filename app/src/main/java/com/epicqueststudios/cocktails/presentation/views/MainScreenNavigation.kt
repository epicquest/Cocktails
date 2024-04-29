package com.epicqueststudios.cocktails.presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.epicqueststudios.cocktails.R
import com.epicqueststudios.cocktails.presentation.viewmodels.CocktailViewModel

@Composable
fun MainScreenNavigation(viewModel: CocktailViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "main_screen"
    ) {

        composable("main_screen") {
            Box(
                modifier = Modifier
                    .fillMaxSize()

            ) {
                MainScreen(viewModel, navController)
            }
        }
        composable(
            route = "detail_screen/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        )
        { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val id = arguments.getString("id")
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = colorResource(R.color.lineColor))
            ) {
                DetailScreen(viewModel, navController, id ?: "")
            }
        }
    }
}
