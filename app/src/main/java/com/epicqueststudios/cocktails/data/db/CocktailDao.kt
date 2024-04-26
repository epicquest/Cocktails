package com.epicqueststudios.cocktails.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.epicqueststudios.cocktails.data.models.CocktailModel

@Dao
interface CocktailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCocktails(cocktails: List<CocktailModel>)

    @Query("SELECT * FROM cocktails")
    suspend fun getCocktails(): List<CocktailModel>
}