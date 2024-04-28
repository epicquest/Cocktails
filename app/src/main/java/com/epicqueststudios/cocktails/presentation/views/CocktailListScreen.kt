package com.epicqueststudios.cocktails.presentation.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.epicqueststudios.cocktails.R
import com.epicqueststudios.cocktails.data.models.CocktailModel
import com.epicqueststudios.cocktails.presentation.viewmodels.CocktailViewModel
import timber.log.Timber


@Composable
fun CocktailListScreen(viewModel: CocktailViewModel, navController: NavHostController) {
    val cocktails = viewModel.cocktails
    val cocktailOfTheDay = viewModel.cocktailOfTheDay.value
    var searchQuery by remember { mutableStateOf("") }

    Card {
    /*Column {
        Text(text = "Cocktail Of The Day", style = MaterialTheme.typography.headlineMedium)
        if (cocktailOfTheDay == null) {
            CircularProgressIndicator()
        } else {
            Card {
                CocktailListItem(cocktailOfTheDay)
            }
        }
    }*/
    Column {
            SearchView(
                modifier = Modifier.padding(16.dp),
                onSearch = { query ->
                    searchQuery = query
                    Timber.d("Search query: $query")
                    viewModel.searchCocktails(searchQuery)
                },
                onTextChange = {
                      viewModel.onTextChange(it)
                }
            )
        }
    Column {
        Text(text = "Cocktails", style = MaterialTheme.typography.headlineMedium)
        if (cocktails.value.isEmpty()) {
            if (searchQuery.isEmpty() || cocktailOfTheDay == null)
                CircularProgressIndicator()
            else
                Text( text = stringResource(R.string.no_cocktails_found), style = MaterialTheme.typography.headlineMedium)
        } else {
            LazyColumn {
                items(cocktails.value) { cocktail ->
                    CocktailListItem(cocktail = cocktail) { selectedItemId ->
                        navController.navigate("detail/${selectedItemId}")
                    }
                }
            }
        }
    }
    }
}

@Composable
fun CocktailListItem(cocktail: CocktailModel, onItemClicked: (String) -> Unit) {
    Card {
        Row(modifier = Modifier.padding(8.dp).clickable {
            onItemClicked(cocktail.idDrink)
        }) {
            Image(
                painter = rememberAsyncImagePainter(cocktail.image),
                contentDescription = cocktail.name,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = cocktail.name, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
@Composable
private fun SearchFieldPlaceholder() {
    Text(
        text = stringResource(R.string.search),
        color = Color.Gray,
        style = TextStyle.Default.copy(fontSize = 16.sp)
    )
}

@Composable
fun SearchView(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit,
    onTextChange: (String) -> Unit
) {
    val searchQuery = remember { mutableStateOf("") }

    TextField(
        value = searchQuery.value,
        onValueChange = {
            onTextChange(it)
            searchQuery.value = it
                        },
        modifier = modifier.fillMaxWidth(),
        placeholder = { SearchFieldPlaceholder() },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            onSearch(searchQuery.value)
        }),
        leadingIcon = {
            IconButton(onClick = { /* Handle leading icon click */ }) {
                Icon(Icons.Default.Home, contentDescription = stringResource(R.string.search_cocktails))
            }
        },
        trailingIcon = {
            IconButton(onClick = {
                if (searchQuery.value.isNotEmpty()) {
                    searchQuery.value = ""
                    onTextChange("")
                }
            }) {
                if (searchQuery.value.isNotEmpty()) {
                    Icon(Icons.Filled.Clear, contentDescription = stringResource(R.string.search_clear) )
                } else {
                    Icon(Icons.Default.Search, contentDescription = stringResource(R.string.start_search))
                }
            }
        }
    )
}
