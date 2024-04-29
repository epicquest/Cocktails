package com.epicqueststudios.cocktails.data.repositories

import com.epicqueststudios.cocktails.data.db.CocktailDao
import com.epicqueststudios.cocktails.data.models.CocktailModel
import com.epicqueststudios.cocktails.data.network.CocktailResponse
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

        //if (cachedCocktails.isEmpty()) {
         //   val networkCocktails = cocktailService.searchCocktails(searchTerm)
         //   if (networkCocktails.drinks?.isNotEmpty() == true)
         //       cocktailDao.insertCocktails(networkCocktails.drinks)
       // }
        return cocktailService.searchCocktails(searchTerm).drinks ?: listOf()
    }

    override suspend fun getCocktails(): List<CocktailModel> {
        val favouriteCocktails = cocktailDao.getFavouritesCocktails()
        if (favouriteCocktails.isNotEmpty()) {
           // favouriteCocktails
        }
        val networkCocktails = cocktailService.downloadCocktailOfTheDay()

        return (networkCocktails.drinks ?: listOf()).plus(favouriteCocktails)
    }

    override suspend fun getCocktailOfTheDay(): CocktailResponse = cocktailService.downloadCocktailOfTheDay()
    override suspend fun getFavouriteCocktails(): List<CocktailModel> = cocktailDao.getFavouritesCocktails()
    override suspend fun insertCocktail(item: CocktailModel) = cocktailDao.insertCocktail(item)


    override suspend fun updateCocktail(item: CocktailModel): CocktailModel {
        cocktailDao.updateCocktail(item)
        return item
    }


    override suspend fun getCocktail(id: String) = cocktailDao.getCocktail(id)

}