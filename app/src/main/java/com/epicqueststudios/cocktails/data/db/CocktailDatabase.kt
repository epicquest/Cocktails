package com.epicqueststudios.cocktails.data.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.epicqueststudios.cocktails.data.models.CocktailModel

@androidx.room.Database(entities = [CocktailModel::class], version = 1, exportSchema = false)
abstract class CocktailDatabase : RoomDatabase() {
    abstract fun cocktailDao(): CocktailDao

    companion object {
        const val DATABASE_NAME = "cocktails.db"

        fun buildDatabase(context: Context): CocktailDatabase {
            return Room.databaseBuilder(context, CocktailDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}
