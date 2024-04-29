package com.epicqueststudios.cocktails.presentation.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import androidx.compose.material3.Text
import androidx.compose.ui.layout.onGloballyPositioned
import com.valentinilk.shimmer.unclippedBoundsInWindow

@Composable
fun Shimmer() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column {
            val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.Window)
            Text(text = "Shimmering Text", modifier = Modifier.shimmer(shimmerInstance))
            Text("Non-shimmering Text")
            Text("Shimmering Text", modifier = Modifier.shimmer(shimmerInstance))
        }
    }
}
@Composable
fun shimmerCocktail() {
    val shimmerInstance = rememberShimmer(ShimmerBounds.Custom)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .onGloballyPositioned { layoutCoordinates ->
                val position = layoutCoordinates.unclippedBoundsInWindow()
                shimmerInstance.updateBounds(position)
            },
    ) {
        Text("Shimmering Text", modifier = Modifier.shimmer(shimmerInstance))
        Text("Non-shimmering Text")
        Text("Shimmering Text", modifier = Modifier.shimmer(shimmerInstance))
    }
}

@Preview
@Composable
fun PreviewShimmer() {
    Shimmer()
}