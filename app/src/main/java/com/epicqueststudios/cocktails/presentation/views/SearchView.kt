package com.epicqueststudios.cocktails.presentation.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epicqueststudios.cocktails.R


@OptIn(ExperimentalMaterial3Api::class)
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
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            containerColor = colorResource(id = R.color.transparent)
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp, 24.dp, 12.dp, 0.dp),
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
            Icon(
                painterResource(R.drawable.ic_cocktail),
                contentDescription = stringResource(R.string.search_cocktails),
                tint = colorResource(id = R.color.icon_color),
                modifier = Modifier.padding(end = 12.dp)
            )
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
                    Icon(
                        Icons.Filled.Clear,
                        tint = colorResource(id = R.color.dark_blue),
                        contentDescription = stringResource(R.string.search_clear)
                    )
                } else {
                    Icon(
                        Icons.Default.Search,
                        tint = colorResource(id = R.color.dark_blue),
                        contentDescription = stringResource(R.string.start_search)
                    )
                }
            }
        }
    )
}

@Composable
private fun SearchFieldPlaceholder() {
    Text(
        text = stringResource(R.string.search),
        color = Color.DarkGray,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.bodyLarge
    )
}

@Preview
@Composable
fun PreviewSearchView() {
    SearchView(onSearch = {}) {
    }
}