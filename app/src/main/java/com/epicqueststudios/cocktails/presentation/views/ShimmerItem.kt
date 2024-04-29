package com.epicqueststudios.cocktails.presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epicqueststudios.cocktails.R
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun ShimmerItem() {
    val shimmerInstance = rememberShimmer(ShimmerBounds.Window)
    Box(
        modifier = Modifier
            .size(dimensionResource(id = R.dimen.image_size))
            .shimmer(shimmerInstance)
            .background(Color.Gray)
    )
    Spacer(
        modifier = Modifier
            .shimmer(shimmerInstance)
            .width(8.dp)
    )
    Column {
        Text(
            "",
            modifier = Modifier
                .padding(8.dp)
                .shimmer(shimmerInstance)
                .background(Color.Gray)
                .fillMaxWidth()
        )
        Text(
            "",
            modifier = Modifier
                .padding(8.dp)
                .shimmer(shimmerInstance)
                .background(Color.Gray)
                .fillMaxWidth()
        )
        Text(
            "",
            modifier = Modifier
                .padding(8.dp)
                .shimmer(shimmerInstance)
                .background(Color.Gray)
                .fillMaxWidth()
        )
    }
}


@Preview
@Composable
fun PreviewShimmerItem() {
    ShimmerItem()
}

