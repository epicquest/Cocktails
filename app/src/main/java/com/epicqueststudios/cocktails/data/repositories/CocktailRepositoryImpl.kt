package com.epicqueststudios.cocktails.data.repositories

import com.epicqueststudios.cocktails.data.db.CocktailDao
import com.epicqueststudios.cocktails.data.models.CocktailModel
import com.epicqueststudios.cocktails.data.services.CocktailService
import com.epicqueststudios.cocktails.presentation.models.Resource

class CocktailRepositoryImpl(
    private val cocktailService: CocktailService,
    private val cocktailDao: CocktailDao
): CocktailRepository() {
    override suspend fun searchCocktails(searchTerm: String): List<Resource<CocktailModel>> =
         (cocktailService.searchCocktails(searchTerm).drinks?: listOf())
            .map { Resource.Success(it) }

    override suspend fun getCocktailOfTheDay(): Resource<CocktailModel>  {
        val cocktail = cocktailService.downloadCocktailOfTheDay().drinks?.firstOrNull()
        return if (cocktail != null) Resource.Success(cocktail) else Resource.Error(null)
    }
    override suspend fun getFavouriteCocktails(): List<Resource<CocktailModel>> =
        cocktailDao.getFavouritesCocktails().map {
        Resource.Success(it)
    }
    override suspend fun insertCocktail(item: CocktailModel) = cocktailDao.insertCocktail(item)


    override suspend fun updateCocktail(item: CocktailModel): CocktailModel {
        cocktailDao.updateCocktail(item)
        return item
    }

    override suspend fun getCocktail(id: String) = cocktailDao.getCocktail(id)
}