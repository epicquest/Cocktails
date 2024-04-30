package com.epicqueststudios.cocktails.presentation.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.epicqueststudios.cocktails.R
import com.epicqueststudios.cocktails.data.models.CocktailModel
import com.epicqueststudios.cocktails.data.models.IngredientModel
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun DetailCocktail(cocktail: CocktailModel) {
    var imagePainterState by remember {
        mutableStateOf<AsyncImagePainter.State>(AsyncImagePainter.State.Empty)
    }
    val imageLoader = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(cocktail.image)
            .size(Size.ORIGINAL)
            .crossfade(true)
            .build(),
        contentScale = ContentScale.Crop,
        onState = { state ->
            imagePainterState = state
        }
    )
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "ID: ${cocktail.idDrink}")
        Text(
            text = cocktail.name,
            modifier = Modifier.padding(8.dp),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge
        )
        when (imagePainterState) {
            is AsyncImagePainter.State.Success ->
                Image(
                    painter = imageLoader,
                    contentDescription = cocktail.name,
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Category:", style = MaterialTheme.typography.bodyMedium)
            Text(
                text = cocktail.category,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Type:", style = MaterialTheme.typography.bodyMedium)
            Text(
                text = cocktail.typeOfCocktail,
                modifier = Modifier.padding(horizontal = 6.dp),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Glass:", style = MaterialTheme.typography.bodyMedium)
            Text(
                text = cocktail.glass,
                modifier = Modifier.padding(horizontal = 6.dp),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = "Ingredients:",
                modifier = Modifier.padding(horizontal = 6.dp),
                style = MaterialTheme.typography.bodyMedium
            )
            LazyColumn {
                items(cocktail.ingredients()) { ingredient ->
                    IngredientListItem(ingredient = ingredient)
                }
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
    Text(text = "${ingredient.ingredient} (${ingredient.measure})")
}

@Preview
@Composable
fun PreviewDetailCocktail() {
    val cardColors = CardColors(
        containerColor = colorResource(id = R.color.cocktail_item_background),
        contentColor = Color.Black,
        disabledContainerColor = Color.LightGray,
        disabledContentColor = Color.DarkGray
    )
    Card(
        colors = cardColors, modifier = Modifier
            .padding(6.dp)
            .fillMaxSize()
    ) {
        DetailCocktail(
            CocktailModel(
                "1",
                "Daiquiri",
                "category name",
                "type of cocktail",
                "glass type",
                "url",
                "",
                "Ingredient1",
                "Measure1",
                "Ingredient2",
                "Measure2"
            )
        )
    }
}

@Preview
@Composable
fun PreviewIngredientListItem() {
    IngredientListItem(IngredientModel("Ingredient", "Measure"))
}