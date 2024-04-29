package com.epicqueststudios.cocktails.presentation.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.epicqueststudios.cocktails.R
import com.epicqueststudios.cocktails.data.models.CocktailModel
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun CocktailListItem(cocktail: CocktailModel?, onItemClicked: () -> Unit) {
    Card {
        Column{
            if (cocktail?.isCocktailOfTheDay == true)
                Text(text = stringResource(R.string.cocktail_of_the_day), style = MaterialTheme.typography.titleMedium)
        }

        Row(modifier = Modifier
            .padding(8.dp)
            .clickable {
                onItemClicked()
            }) {
            if (cocktail != null) {
                Image(
                    painter = rememberAsyncImagePainter(cocktail.image),
                    contentDescription = cocktail.name,
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = cocktail.name, style = MaterialTheme.typography.bodyMedium)
                }
            } else {
                val shimmerInstance = rememberShimmer(ShimmerBounds.Custom)
                Image(
                    painter = rememberAsyncImagePainter(""),
                    contentDescription = "",
                    modifier = Modifier.shimmer(shimmerInstance).size(80.dp)
                )
                Spacer(modifier = Modifier.shimmer(shimmerInstance).width(8.dp))
                Column {
                    Text(text = "", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.shimmer(shimmerInstance))
                }
            }
        }
    }
}