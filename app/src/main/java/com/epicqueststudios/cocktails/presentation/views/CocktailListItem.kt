package com.epicqueststudios.cocktails.presentation.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.epicqueststudios.cocktails.R
import com.epicqueststudios.cocktails.data.models.CocktailModel
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun CocktailListItem(cocktail: CocktailModel?, onItemClicked: () -> Unit) {
    var imagePainterState by remember {
        mutableStateOf<AsyncImagePainter.State>(AsyncImagePainter.State.Empty)
    }
    val imageLoader = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(cocktail?.image ?: "")
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
            if (cocktail?.isCocktailOfTheDay != false)
                Text(
                    text = stringResource(R.string.cocktail_of_the_day),
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.dark_green),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxSize()
                        .background(colorResource(id = R.color.light_green))
                )
        }

        Row(modifier = Modifier
            .padding(8.dp)
            .clickable {
                onItemClicked()
            }) {
            if (cocktail != null) {
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
                    Text(text = cocktail.name, style = MaterialTheme.typography.bodyMedium)
                    Text(text = cocktail.name, style = MaterialTheme.typography.bodySmall)
                }
            } else {
                ShimmerItem()
            }
        }
    }
}


@Preview
@Composable
fun PreviewCocktailListItem() {
    CocktailListItem(
        null
    ) {}
}
