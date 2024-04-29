package com.epicqueststudios.cocktails.domain

import com.epicqueststudios.cocktails.data.models.CocktailModel
import com.epicqueststudios.cocktails.data.repositories.CocktailRepository
import com.epicqueststudios.cocktails.presentation.models.Resource
import timber.log.Timber

class CocktailsUseCase(private val repository: CocktailRepository) {
    suspend fun getCocktailOfTheDay(): Resource<CocktailModel> {
        return try {
            repository.getCocktailOfTheDay().also { it.data?.isCocktailOfTheDay = true }
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e.message)
        }
    }
    suspend fun getFavourites(): List<Resource<CocktailModel>> {
        return repository.getFavouriteCocktails()
    }
    suspend fun insertCocktail(item: CocktailModel) {
        return repository.insertCocktail(item)
    }
    suspend fun getCocktail(id: String): CocktailModel? = repository.getCocktail(id)
    suspend fun updateCocktail(item: CocktailModel) = repository.updateCocktail(item)


}
