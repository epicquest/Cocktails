package com.epicqueststudios.cocktails.domain

import com.epicqueststudios.cocktails.data.models.CocktailModel
import com.epicqueststudios.cocktails.data.repositories.CocktailRepository

class CocktailsUseCase(private val repository: CocktailRepository) {
    suspend fun getCocktailOfTheDay(): CocktailModel? {
        return repository.getCocktailOfTheDay().drinks?.first()
    }
    suspend fun getFavourites(): List<CocktailModel> {
        return repository.getFavouriteCocktails()
    }
    suspend fun insertCocktail(item: CocktailModel) {
        return repository.insertCocktail(item)
    }
    suspend fun getCocktail(id: String): CocktailModel? = repository.getCocktail(id)
    suspend fun updateCocktail(item: CocktailModel) = repository.updateCocktail(item)


}
