package com.epicqueststudios.cocktails.domain

import com.epicqueststudios.cocktails.data.models.CocktailModel
import com.epicqueststudios.cocktails.data.repositories.CocktailRepository
import com.epicqueststudios.cocktails.presentation.models.Resource

class SearchCocktailsUseCase(private val repository: CocktailRepository) {
    suspend fun getCocktails(searchTerm: String): List<Resource<CocktailModel>> =
        repository.searchCocktails(searchTerm)
}
