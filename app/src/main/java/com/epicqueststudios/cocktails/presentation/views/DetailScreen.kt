package com.epicqueststudios.cocktails.presentation.views


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.epicqueststudios.cocktails.R
import com.epicqueststudios.cocktails.presentation.viewmodels.CocktailViewModel

@Composable
fun DetailScreen(
    viewModel: CocktailViewModel?,
    navController: NavHostController?
) {
    val cocktail = viewModel?.selectedCocktail

    val addText = stringResource(R.string.add_from_favorites)
    val removeText = stringResource(R.string.remove_from_favorites)
    val btnFavorites = remember { mutableStateOf(addText) }
    btnFavorites.value = if (cocktail?.value?.isFavourite == true) removeText else addText
    val cardColors = CardColors(
        containerColor = colorResource(id = R.color.cocktail_item_background),
        contentColor = Color.Black,
        disabledContainerColor = Color.LightGray,
        disabledContentColor = Color.DarkGray
    )

    val buttonColors = ButtonColors(
        containerColor = colorResource(id = R.color.button_background),
        contentColor = Color.Black,
        disabledContainerColor = Color.LightGray,
        disabledContentColor = Color.DarkGray
    )
    Card(
        colors = cardColors, modifier = Modifier
            .padding(6.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (cocktail?.value == null) {
                Text(
                    text = stringResource(R.string.cocktail_detail),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(6.dp)
                        .fillMaxWidth()
                )

                Column {
                    Text(
                        text = stringResource(R.string.error_message),
                        textAlign = TextAlign.Center,
                        color = Color.Red,
                        modifier = Modifier
                            .padding(6.dp)
                            .fillMaxWidth()
                    )

                }
            } else {
                DetailCocktail(cocktail.value!!)
                Button(
                    onClick = {
                        viewModel.updateFavorites(cocktail.value)
                        btnFavorites.value =
                            if (cocktail.value?.isFavourite != true) addText else removeText
                    },
                    colors = buttonColors
                ) {
                    Text(text = btnFavorites.value)
                }
            }
            Button(
                onClick = {
                    navController?.popBackStack()
                    viewModel?.getCocktailOfTheDayAndFavorites()
                }, modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(0.8f),
                colors = buttonColors
            ) {
                Text(text = stringResource(id = R.string.go_back))
            }
        }
    }
}

@Preview
@Composable
fun PreviewDetailScreen() {
    DetailScreen(null, null)
}
