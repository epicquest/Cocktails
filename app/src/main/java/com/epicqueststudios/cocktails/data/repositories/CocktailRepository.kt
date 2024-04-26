package com.epicqueststudios.cocktails.data.repositories

import com.epicqueststudios.cocktails.data.models.CocktailModel

abstract class CocktailRepository {
    abstract suspend fun getCocktails(searchTerm: String): List<CocktailModel>
}