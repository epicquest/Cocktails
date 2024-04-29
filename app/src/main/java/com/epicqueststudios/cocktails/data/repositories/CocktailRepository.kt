package com.epicqueststudios.cocktails.data.repositories

import com.epicqueststudios.cocktails.data.models.CocktailModel
import com.epicqueststudios.cocktails.presentation.models.Resource

abstract class CocktailRepository {
    abstract suspend fun searchCocktails(searchTerm: String): List<Resource<CocktailModel>>
    abstract suspend fun getCocktailOfTheDay(): Resource<CocktailModel>
    abstract suspend fun getFavouriteCocktails(): List<Resource<CocktailModel>>
    abstract suspend fun insertCocktail(item: CocktailModel)
    abstract suspend fun updateCocktail(item: CocktailModel): CocktailModel
    abstract suspend fun getCocktail(id: String): CocktailModel?
}