package com.epicqueststudios.cocktails.presentation.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.epicqueststudios.cocktails.R
import com.epicqueststudios.cocktails.presentation.models.SearchState
import com.epicqueststudios.cocktails.presentation.viewmodels.CocktailViewModel
import timber.log.Timber


@Composable
fun MainScreen(viewModel: CocktailViewModel, navController: NavHostController) {
    val cocktails = viewModel.cocktails
    val searchState = viewModel.searchState

    Card {
    Column {
            SearchView(
                modifier = Modifier.padding(16.dp),
                onSearch = { query ->
                    viewModel.searchCocktails(query ?: "")
                },
                onTextChange = {
                      viewModel.onTextChange(it)
                }
            )
        }
    Column {
        Text(text = stringResource(R.string.cocktails), style = MaterialTheme.typography.headlineMedium)
        if (cocktails.value.isEmpty()) {
            when(searchState.value) {
                is SearchState.Error ->  Text( text = searchState.value.message ?: stringResource(R.string.error_message), style = MaterialTheme.typography.bodyMedium)
                is SearchState.Idle -> {}
                is SearchState.Loading ->  CircularProgressIndicator()
                is SearchState.Success ->  if (searchState.value.data?.filterNotNull()?.isEmpty() == true) {
                    Text(text = stringResource(R.string.no_cocktails_found))
                }
            }
        } else {
            LazyColumn {
                items(cocktails.value) { cocktail ->
                    CocktailListItem(cocktail = cocktail) {
                        if (cocktail != null) {
                            viewModel.insertCocktail(cocktail)
                            navController.navigate("detail_screen/${cocktail.idDrink}")
                        }
                    }
                }
            }
        }
    }
    }
}


@Composable
private fun SearchFieldPlaceholder() {
    Text(
        text = stringResource(R.string.search),
        color = Color.Gray,
        style = MaterialTheme.typography.titleMedium
    )
}

@Composable
fun SearchView(
    modifier: Modifier = Modifier,
    onSearch: (String?) -> Unit,
    onTextChange: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val searchQuery = remember { mutableStateOf<String?>(null) }

    TextField(
        value = searchQuery.value ?: "",
        onValueChange = {
            onTextChange(it)
            searchQuery.value = it
                        },
        modifier = modifier.fillMaxWidth(),
        placeholder = { SearchFieldPlaceholder() },
        singleLine = true,
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 21.sp
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            onSearch(searchQuery.value)
            keyboardController?.hide()
        }),
        leadingIcon = {
           Icon(painterResource(R.drawable.ic_cocktail), contentDescription = stringResource(R.string.search_cocktails))
        },
        trailingIcon = {
            IconButton(onClick = {
                if (searchQuery.value != null) {
                    searchQuery.value = null
                    onTextChange("")
                    keyboardController?.hide()
                }
            }) {
                if (searchQuery.value != null) {
                    Icon(Icons.Filled.Clear, contentDescription = stringResource(R.string.search_clear) )
                } else {
                    Icon(Icons.Default.Search, contentDescription = stringResource(R.string.start_search))
                }
            }
        }
    )
}

