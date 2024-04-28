package com.epicqueststudios.cocktails.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "cocktails")
data class CocktailModel(
    @PrimaryKey(autoGenerate = false)
    val idDrink: String,
    @SerializedName("strDrink") val name: String,
    @SerializedName("strDrinkThumb") val image: String,
    @SerializedName("strInstructions") val instructions: String,
    var isFavourite: Boolean = false
)