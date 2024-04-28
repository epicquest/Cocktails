package com.epicqueststudios.cocktails.presentation.views

import android.widget.SearchView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.epicqueststudios.cocktails.data.models.CocktailModel
import com.epicqueststudios.cocktails.presentation.viewmodels.CocktailViewModel
import timber.log.Timber


@Composable
fun CocktailListScreen(viewModel: CocktailViewModel) {
    val cocktails = viewModel.cocktails.value
    val cocktailOfTheDay = viewModel.cocktailOfTheDay.value
    var searchQuery by remember { mutableStateOf("") }

    Card {
    Column {
        Text(text = "Cocktail Of The Day", style = MaterialTheme.typography.headlineMedium)
        if (cocktailOfTheDay == null) {
            CircularProgressIndicator()
        } else {
            Card {
                CocktailListItem(cocktailOfTheDay)
            }
        }
    }
        Column {
            SearchView(
                modifier = Modifier.padding(16.dp),
                onSearch = { query ->
                    searchQuery = query
                    Timber.d("Search query: $query")
                    viewModel.searchCocktails(searchQuery)
                }
            )
        }
    Column {
        Text(text = "Cocktails", style = MaterialTheme.typography.headlineMedium)
        if (cocktails.isEmpty()) {
            Text(text = "No Favourites", style = MaterialTheme.typography.headlineMedium)
        } else {
            LazyColumn {
                items(cocktails) { cocktail ->
                    CocktailListItem(cocktail = cocktail)
                }
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
@Composable
private fun SearchFieldPlaceholder() {
    Text(
        text = "Search...",
        color = Color.Gray,
        style = TextStyle.Default.copy(fontSize = 16.sp)
    )
}

@Composable
fun SearchView(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit
) {
    val searchQuery = remember { mutableStateOf("") }

    TextField(
        value = searchQuery.value,
        onValueChange = { searchQuery.value = it },
        modifier = modifier.fillMaxWidth(),
        placeholder = { SearchFieldPlaceholder() },
        singleLine = true,
      /*  colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent
        ),*/
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            onSearch(searchQuery.value)
        }),
        leadingIcon = {
            IconButton(onClick = { /* Handle leading icon click */ }) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_input_get),
                    contentDescription = null
                )
            }
        },
        trailingIcon = {
            if (searchQuery.value.isNotEmpty()) {
                IconButton(onClick = { searchQuery.value = "" }) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_menu_close_clear_cancel),
                        contentDescription = null
                    )
                }
            }
        }
    )
}
/*
@Composable
fun SearchView(
    searchText: String,
    onQueryChange: (String) -> Unit,
    isSearchExpanded: Boolean,
    onToggleSearch: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = searchText,
            onValueChange = onQueryChange,
            modifier = Modifier.fillMaxWidth(0.8f),
            placeholder = "Search...",
            trailingIcon = {
                IconButton(onClick = onToggleSearch) {
                    if (isSearchExpanded) {
                        Icon(Icons.Filled.Close, contentDescription = "Clear search")
                    } else {
                        Icon(Icons.Filled.Search, contentDescription = "Start search")
                    }
                }
            }
        )
        if (isSearchExpanded) {
            // Additional search options UI
        }
    }
}
*/
