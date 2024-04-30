package com.epicqueststudios.cocktails.presentation.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
    val buttonColors = ButtonColors (
        containerColor= colorResource(id = R.color.button_background),
        contentColor = Color.White,
        disabledContainerColor = Color.LightGray,
        disabledContentColor =  Color.DarkGray
    )
    if (cocktails.value.isEmpty()) {
        when (searchState.value) {
            is SearchState.Error -> {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text( text = searchState.value.message ?: stringResource(
                        R.string.unknown_error
                    ),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp))
                    Button(
                        onClick = {
                            viewModel.searchCocktails()
                        }, colors = buttonColors
                    ) {
                        Text(text = stringResource(id = R.string.retry))
                    }
                }
            }
            is SearchState.Idle -> {}
            is SearchState.Loading ->
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                }
            is SearchState.Success -> if (searchState.value.data?.isEmpty() == true
            ) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = stringResource(R.string.no_cocktails_found),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .padding(12.dp))
                }
            }
        }
    } else {
        LazyColumn {
            items(cocktails.value) { cocktail ->
                CocktailListItem(cocktailResource = cocktail, {
                    if (cocktail is Resource.Success) {
                        viewModel.insertCocktail(cocktail.data!!)
                        navController.navigate("detail_screen/${cocktail.data.idDrink}")
                        viewModel.getCocktail(cocktail.data.idDrink)
                    }
                }, {
                    viewModel.getCocktailOfTheDayAndFavorites()
                })
            }
        }
    }
}