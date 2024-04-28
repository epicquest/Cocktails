package com.epicqueststudios.cocktails.presentation.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.epicqueststudios.cocktails.presentation.viewmodels.CocktailViewModel

@Composable
fun CocktailDetailScreen(
    viewModel: CocktailViewModel,
    navController: NavHostController,
    id: String
) {
    Text(text = "Screen 2, ID: $id")
    Button(onClick = { navController.popBackStack() }) {
        Text(text = "Go to Screen 1")
    }
    /*val item = viewModel.selectedItem.value

    if (item != null) {
        Column {
            Text(text = "Item Details")
            Image(
                painter = rememberAsyncImagePainter(item.image),
                contentDescription = item.name,
                modifier = Modifier.size(80.dp)
            )
            Text(text = "Name: ${item.name}")
            Text(text = "Description: ${item.instructions}")
        }
    } else {
        // Handle loading or error states (optional)
    }*/
}