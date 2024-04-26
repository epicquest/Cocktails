package com.epicqueststudios.cocktails.presentation.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.epicqueststudios.cocktails.data.models.CocktailModel
import com.epicqueststudios.cocktails.presentation.viewmodels.CocktailViewModel

@Composable
fun CocktailListScreen(viewModel: CocktailViewModel) {
    val cocktails = viewModel.cocktails.value

    Column {
        Text(text = "Cocktails", style = MaterialTheme.typography.headlineMedium)
        if (cocktails.isEmpty()) {
            CircularProgressIndicator()
        } else {
            LazyColumn {
                items(cocktails) { cocktail ->
                    CocktailListItem(cocktail = cocktail)
                }
            }
        }
    }
}

@Composable
fun CocktailListItem(cocktail: CocktailModel) {
    Card {
        Row(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = rememberAsyncImagePainter(cocktail.image),
                contentDescription = cocktail.name,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = cocktail.name, style = MaterialTheme.typography.bodyMedium)
                // Optional: Show instructions with another Text composable
            }
        }
    }
}