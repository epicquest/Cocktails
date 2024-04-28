package com.epicqueststudios.cocktails.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.epicqueststudios.cocktails.data.models.CocktailModel

@Dao
interface CocktailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCocktails(cocktails: List<CocktailModel>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCocktail(cocktail: CocktailModel)
    @Update()
    suspend fun updateCocktail(cocktail: CocktailModel): Int
    @Query("SELECT * FROM cocktails")
    suspend fun getCocktails(): List<CocktailModel>
    @Query("SELECT * FROM cocktails WHERE isFavourite = 1")
    suspend fun getFavouritesCocktails(): List<CocktailModel>
    @Query("SELECT * FROM cocktails WHERE idDrink = :id")
    suspend fun getCocktail(id: String): CocktailModel?
}