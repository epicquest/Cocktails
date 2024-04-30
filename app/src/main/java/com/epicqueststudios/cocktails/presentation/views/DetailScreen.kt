package com.epicqueststudios.cocktails.presentation.views


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.epicqueststudios.cocktails.R
import com.epicqueststudios.cocktails.data.models.CocktailModel
import com.epicqueststudios.cocktails.data.models.IngredientModel
import com.epicqueststudios.cocktails.presentation.viewmodels.CocktailViewModel
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun DetailScreen(
    viewModel: CocktailViewModel,
    navController: NavHostController,
    id: String
) {
    var imagePainterState by remember {
        mutableStateOf<AsyncImagePainter.State>(AsyncImagePainter.State.Empty)
    }
    val addText = stringResource(R.string.add_from_favorites)
    val removeText = stringResource(R.string.remove_from_favorites)
    val btnFavorites = remember { mutableStateOf(addText ) }
    val cocktail = viewModel.selectedCocktail
    btnFavorites.value = if (cocktail.value?.isFavourite == true) removeText else addText

    val imageLoader = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(cocktail.value?.image ?: "")
            .size(Size.ORIGINAL)
            .crossfade(true)
            .build(),
        contentScale = ContentScale.Crop,
        onState = { state ->
            imagePainterState = state
        }
    )
    Card {
         Column {
             Text(text = stringResource(R.string.cocktail_detail))
             if (cocktail.value == null) {
             Column {
                 Text(text = stringResource(R.string.error_message))
             } } else {
                 when (imagePainterState) {
                     is AsyncImagePainter.State.Success ->
                         Image(
                             painter = imageLoader,
                             contentDescription = cocktail.value?.name,
                             modifier = Modifier.size(dimensionResource(id = R.dimen.image_detail_size))
                         )

                     is AsyncImagePainter.State.Loading -> {
                         val shimmerInstance = rememberShimmer(ShimmerBounds.Window)
                         Box(
                             modifier = Modifier
                                 .size(dimensionResource(id = R.dimen.image_detail_size))
                                 .shimmer(shimmerInstance)
                                 .background(Color.Gray)
                         )
                     }

                     else ->
                         Box(
                             modifier = Modifier
                                 .size(dimensionResource(id = R.dimen.image_detail_size))
                                 .background(Color.Gray)
                         )
                 }


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
             Button(onClick = {
                 navController.popBackStack()
                 viewModel.getCocktailOfTheDayAndFavorites()
             }) {
                 Text(text = "Go Back")
             }
         }
     }
}

private fun CocktailModel.ingredients(): List<IngredientModel> = listOf(
    IngredientModel(this.ingredient1, this.measure1),
    IngredientModel(this.ingredient2, this.measure2),
    IngredientModel(this.ingredient3, this.measure3),
    IngredientModel(this.ingredient4, this.measure4),
    IngredientModel(this.ingredient5, this.measure5),
    IngredientModel(this.ingredient6, this.measure6),
    IngredientModel(this.ingredient7, this.measure7),
    IngredientModel(this.ingredient8, this.measure8),
    IngredientModel(this.ingredient9, this.measure9),
    IngredientModel(this.ingredient10, this.measure10),
    IngredientModel(this.ingredient11, this.measure11),
    IngredientModel(this.ingredient12, this.measure12),
    IngredientModel(this.ingredient13, this.measure13),
    IngredientModel(this.ingredient14, this.measure14),
    IngredientModel(this.ingredient15, this.measure15)
    ).filter { it.ingredient != null && it.measure != null }

@Composable
fun IngredientListItem(ingredient: IngredientModel) {
    Text(text = "${ingredient.ingredient} ${ingredient.measure}")
}
