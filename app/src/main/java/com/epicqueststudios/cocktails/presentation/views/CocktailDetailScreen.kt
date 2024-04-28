package com.epicqueststudios.cocktails.presentation.views


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.epicqueststudios.cocktails.R
import com.epicqueststudios.cocktails.data.models.CocktailModel
import com.epicqueststudios.cocktails.data.models.IngredientModel
import com.epicqueststudios.cocktails.presentation.viewmodels.CocktailViewModel

@Composable
fun CocktailDetailScreen(
    viewModel: CocktailViewModel,
    navController: NavHostController,
    id: String
) {
    val addText = stringResource(R.string.add_from_favorites)
    val removeText = stringResource(R.string.remove_from_favorites)
    val btnFavorites = remember { mutableStateOf(addText) }
    val cocktail = viewModel.selectedCocktail

     Card {
         Column {
             Text(text = stringResource(R.string.cocktail_detail))
             if (cocktail.value == null) {
             Column {
                 Text(text = stringResource(R.string.error_message))
             } } else {
                 Image(
                     painter = rememberAsyncImagePainter(cocktail.value?.image),
                     contentDescription = cocktail.value?.name,
                     modifier = Modifier.size(80.dp)
                 )
                 Text(text = "Name: ${cocktail.value?.name}")
                 Text(text = "ID: ${cocktail.value?.idDrink}")
                 Text(text = "Category: ${cocktail.value?.category}")
                 Text(text = "Type: ${cocktail.value?.typeOfCocktail}")
                 Text(text = "Glass: ${cocktail.value?.glass}")
                 Text(text = "Ingredients:")
                 LazyColumn {
                     items(cocktail.value?.ingredients() ?: listOf()) { ingredient ->
                            IngredientListItem(ingredient = ingredient)
                     }
                 }
                 Button(onClick = {
                     viewModel.updateFavorites(cocktail.value)
                     btnFavorites.value = if (cocktail.value?.isFavourite != true) addText else removeText
                 }) {
                     Text(text = btnFavorites.value)
                 }
             }
             Button(onClick = { navController.popBackStack() }) {
                 Text(text = "Go Back")
             }
         }
     }
}

private fun CocktailModel.ingredients(): List<IngredientModel> = listOf(IngredientModel(this.ingredient1, this.measure1)).filter { it.ingredient != null && it.measure != null }

@Composable
fun IngredientListItem(ingredient: IngredientModel) {
    Text(text = "${ingredient.ingredient} ${ingredient.measure}")
}
