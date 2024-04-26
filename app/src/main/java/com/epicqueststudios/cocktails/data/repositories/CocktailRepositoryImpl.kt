package com.epicqueststudios.cocktails.data.repositories

import com.epicqueststudios.cocktails.data.db.CocktailDao
import com.epicqueststudios.cocktails.data.models.CocktailModel
import com.epicqueststudios.cocktails.data.services.CocktailService

class CocktailRepositoryImpl(
    private val cocktailService: CocktailService,
    private val cocktailDao: CocktailDao
): CocktailRepository() {

    override suspend fun getCocktails(searchTerm: String): List<CocktailModel> {
        /*val cachedCocktails = cocktailDao.getCocktails()
        if (cachedCocktails.isEmpty()) {
            val response = cocktailService.searchCocktails(searchTerm)
            cocktailDao.insertCocktails(response.drinks)
            return response.drinks
        } else {
            return cachedCocktails
        }*/
        val cachedCocktails = cocktailDao.getCocktails()
        if (cachedCocktails.isEmpty()) {
            val networkCocktails = cocktailService.searchCocktails(searchTerm)
            cocktailDao.insertCocktails(networkCocktails.drinks)
        }
        return cocktailDao.getCocktails()
    }
}