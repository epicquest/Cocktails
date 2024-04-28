package com.epicqueststudios.cocktails.data.repositories

import com.epicqueststudios.cocktails.data.models.CocktailModel
import com.epicqueststudios.cocktails.data.network.CocktailResponse

abstract class CocktailRepository {
    abstract suspend fun getCocktails(searchTerm: String): List<CocktailModel>
    abstract suspend fun getCocktailOfTheDay(): CocktailResponse
}