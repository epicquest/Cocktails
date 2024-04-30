package com.epicqueststudios.cocktails.presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.epicqueststudios.cocktails.R
import com.epicqueststudios.cocktails.presentation.viewmodels.CocktailViewModel


@Composable
fun MainScreen(viewModel: CocktailViewModel, navController: NavHostController) {
    Card(
        shape = RectangleShape, colors = CardColors(
            containerColor = colorResource(id = R.color.app_background),
            contentColor = Color.Black,
            disabledContainerColor = Color.LightGray,
            disabledContentColor = Color.DarkGray
        )
    ) {
        Column {
            SearchView(
                onSearch = { query ->
                    viewModel.searchCocktails(query ?: "")
                },
                onTextChange = {
                    viewModel.onTextChange(it)
                }
            )
        }
        Column {
            Box(
                modifier = Modifier
                    .height(16.dp)
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .background(color = colorResource(R.color.lineColor))
            )
        }

        Column {
            MainScreenList(viewModel, navController)
        }
    }
}

