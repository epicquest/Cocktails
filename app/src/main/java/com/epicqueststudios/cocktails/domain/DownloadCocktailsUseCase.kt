package com.epicqueststudios.cocktails.domain

import com.epicqueststudios.cocktails.data.models.CocktailModel
import com.epicqueststudios.cocktails.data.repositories.CocktailRepository

class DownloadCocktailsUseCase(private val repository: CocktailRepository) {
    suspend fun getCocktails(searchTerm: String): List<CocktailModel> =
        repository.getCocktails(searchTerm)
}
