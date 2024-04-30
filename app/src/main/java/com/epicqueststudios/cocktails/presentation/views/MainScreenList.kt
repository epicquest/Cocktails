package com.epicqueststudios.cocktails.presentation.views

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.epicqueststudios.cocktails.R
import com.epicqueststudios.cocktails.presentation.models.Resource
import com.epicqueststudios.cocktails.presentation.models.SearchState
import com.epicqueststudios.cocktails.presentation.viewmodels.CocktailViewModel

@Composable
fun MainScreenList(
    viewModel: CocktailViewModel,
    navController: NavHostController
) {
    val cocktails = viewModel.cocktails
    val searchState = viewModel.searchState

    if (cocktails.value.isEmpty()) {
        when (searchState.value) {
            is SearchState.Error -> Text(
                text = searchState.value.message ?: stringResource(
                    R.string.error_message
                ), style = MaterialTheme.typography.bodyMedium
            )

            is SearchState.Idle -> {}
            is SearchState.Loading -> CircularProgressIndicator()
            is SearchState.Success -> if (searchState.value.data?.filterNotNull()
                    ?.isEmpty() == true
            ) {
                Text(text = stringResource(R.string.no_cocktails_found))
            }
        }
    } else {
        LazyColumn {
            items(cocktails.value) { cocktail ->
                CocktailListItem(cocktailResource = cocktail, {
                    if (cocktail is Resource.Success) {
                        viewModel.insertCocktail(cocktail.data!!)
                        navController.navigate("detail_screen/${cocktail.data.idDrink}")
                    }
                }, {
                    viewModel.getCocktailOfTheDayAndFavorites()
                })
            }
        }
    }
}