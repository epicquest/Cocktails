package com.epicqueststudios.cocktails.data.services

import com.epicqueststudios.cocktails.data.network.CocktailResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailService {

    @GET("search.php")
    suspend fun searchCocktails(@Query("s") searchTerm: String): CocktailResponse
    @GET("random.php")
    suspend fun downloadCocktailOfTheDay(): CocktailResponse

}