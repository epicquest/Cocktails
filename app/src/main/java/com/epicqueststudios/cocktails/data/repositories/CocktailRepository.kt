package com.epicqueststudios.cocktails.data.repositories

import com.epicqueststudios.cocktails.data.models.CocktailModel
import com.epicqueststudios.cocktails.data.network.CocktailResponse

abstract class CocktailRepository {
    abstract suspend fun getCocktails(searchTerm: String): List<CocktailModel>
    abstract suspend fun getCocktailOfTheDay(): CocktailResponse
    abstract suspend fun getCocktails(): List<CocktailModel>
    abstract suspend fun getFavouriteCocktails(): List<CocktailModel>
    abstract suspend fun insertCocktail(item: CocktailModel)
    abstract suspend fun updateCocktail(item: CocktailModel): CocktailModel
    abstract suspend fun getCocktail(id: String): CocktailModel?
}