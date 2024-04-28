package com.epicqueststudios.cocktails.domain

import com.epicqueststudios.cocktails.data.models.CocktailModel
import com.epicqueststudios.cocktails.data.repositories.CocktailRepository

class CocktailOfTheDayUseCase(private val repository: CocktailRepository) {
    suspend fun getCocktail(): CocktailModel {
        return repository.getCocktailOfTheDay().drinks.first()
    }
}
