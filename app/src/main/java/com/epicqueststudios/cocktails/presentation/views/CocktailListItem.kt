package com.epicqueststudios.cocktails.presentation.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.epicqueststudios.cocktails.R
import com.epicqueststudios.cocktails.data.models.CocktailModel
import com.epicqueststudios.cocktails.presentation.models.Resource
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun CocktailListItem(cocktailResource: Resource<CocktailModel>, onItemClicked: () -> Unit, retry: () -> Unit, ) {
    var imagePainterState by remember {
        mutableStateOf<AsyncImagePainter.State>(AsyncImagePainter.State.Empty)
    }

    val cardColors = CardColors (
        containerColor= colorResource(id = R.color.cocktail_item_background),
        contentColor = Color.Black,
        disabledContainerColor = Color.LightGray,
        disabledContentColor =  Color.DarkGray
    )

    val buttonColors = ButtonColors (
        containerColor= colorResource(id = R.color.button_background),
        contentColor = Color.White,
        disabledContainerColor = Color.LightGray,
        disabledContentColor =  Color.DarkGray
    )
    val imageLoader = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(cocktailResource.data?.image ?: "")
            .size(Size.ORIGINAL)
            .crossfade(true)
            .build(),
        contentScale = ContentScale.Crop,
        onState = { state ->
            imagePainterState = state
        }
    )
    Card(colors = cardColors, modifier = Modifier.padding(4.dp)) {
        Column {
            if (cocktailResource.data?.isCocktailOfTheDay == true)
                Text(
                    text = stringResource(R.string.cocktail_of_the_day),
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.white),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colorResource(id = R.color.cocktail_of_the_day_badge))
                )
        }

        Row(modifier = Modifier
            .padding(8.dp).fillMaxWidth()
            .clickable {
                onItemClicked()
            }) {
            when (cocktailResource) {
                is Resource.Success -> {
                    val cocktail = cocktailResource.data!!
                    when (imagePainterState) {
                        is AsyncImagePainter.State.Success ->
                            Image(
                                painter = imageLoader,
                                contentDescription = cocktail.name,
                                modifier = Modifier.size(dimensionResource(R.dimen.image_size))
                            )

                        is AsyncImagePainter.State.Loading -> {
                            val shimmerInstance = rememberShimmer(ShimmerBounds.Window)
                            Box(
                                modifier = Modifier
                                    .size(dimensionResource(id = R.dimen.image_size))
                                    .shimmer(shimmerInstance)
                                    .background(Color.Gray)
                            )
                        }

                        else ->
                            Box(
                                modifier = Modifier
                                    .size(dimensionResource(id = R.dimen.image_size))
                                    .background(Color.Gray)
                            )
                    }

                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(text = cocktail.name, style = MaterialTheme.typography.titleLarge)
                        Text(text = cocktail.category, style = MaterialTheme.typography.bodyMedium)
                        Text(text = cocktail.typeOfCocktail, style = MaterialTheme.typography.bodyMedium)
                    }
                }

                is Resource.Loading -> {
                    ShimmerItem()
                }

                is Resource.Error -> {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = cocktailResource.message?: stringResource(id = R.string.unknown_error),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            color = Color.Red,
                            fontWeight = Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp))
                        Button(
                            onClick = {
                            retry()
                        }, colors = buttonColors
                        ) {
                            Text(text = stringResource(id = R.string.retry))
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewCocktailOfTheDayListItem() {
    CocktailListItem(
        Resource.Success(CocktailModel("1", "test name", "category name", "type of cocktail", "glass type", "url").apply { isCocktailOfTheDay = true }), {}, {})
}
@Preview
@Composable
fun PreviewCocktailListItemSuccess() {
    CocktailListItem(
        Resource.Success(CocktailModel("1", "test name", "category name", "type of cocktail", "glass type", "url")), {}, {})
}

@Preview
@Composable
fun PreviewCocktailListItemError() {
    CocktailListItem(
        Resource.Error("error message"), {}, {})
}

@Preview
@Composable
fun PreviewCocktailListItemLoading() {
    CocktailListItem(
        Resource.Loading(), {}, {})
}
