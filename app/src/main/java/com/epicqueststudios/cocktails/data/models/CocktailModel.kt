package com.epicqueststudios.cocktails.data.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "cocktails")
data class CocktailModel(
    @PrimaryKey(autoGenerate = false)
    val idDrink: String,
    @SerializedName("strDrink") val name: String,
    @SerializedName("strCategory") val category: String,
    @SerializedName("strAlcoholic") val typeOfCocktail: String,
    @SerializedName("strGlass") val glass: String,
    @SerializedName("strDrinkThumb") val image: String,
    @SerializedName("strInstructions") val instructions: String? = null,
    @SerializedName("strIngredient1") val ingredient1: String? = null,
    @SerializedName("strMeasure1") val measure1: String? = null,
    @SerializedName("strIngredient2") val ingredient2: String? = null,
    @SerializedName("strMeasure2") val measure2: String? = null,
    @SerializedName("strIngredient3") val ingredient3: String? = null,
    @SerializedName("strMeasure3") val measure3: String? = null,
    @SerializedName("strIngredient4") val ingredient4: String? = null,
    @SerializedName("strMeasure4") val measure4: String? = null,
    @SerializedName("strIngredient5") val ingredient5: String? = null,
    @SerializedName("strMeasure5") val measure5: String? = null,
    @SerializedName("strIngredient6") val ingredient6: String? = null,
    @SerializedName("strMeasure6") val measure6: String? = null,
    @SerializedName("strIngredient7") val ingredient7: String? = null,
    @SerializedName("strMeasure7") val measure7: String? = null,
    @SerializedName("strIngredient8") val ingredient8: String? = null,
    @SerializedName("strMeasure8") val measure8: String? = null,
    @SerializedName("strIngredient9") val ingredient9: String? = null,
    @SerializedName("strMeasure9") val measure9: String? = null,
    @SerializedName("strIngredient10") val ingredient10: String? = null,
    @SerializedName("strMeasure10") val measure10: String? = null,
    @SerializedName("strIngredient11") val ingredient11: String? = null,
    @SerializedName("strMeasure11") val measure11: String? = null,
    @SerializedName("strIngredient12") val ingredient12: String? = null,
    @SerializedName("strMeasure12") val measure12: String? = null,
    @SerializedName("strIngredient13") val ingredient13: String? = null,
    @SerializedName("strMeasure13") val measure13: String? = null,
    @SerializedName("strIngredient14") val ingredient14: String? = null,
    @SerializedName("strMeasure14") val measure14: String? = null,
    @SerializedName("strIngredient15") val ingredient15: String? = null,
    @SerializedName("strMeasure15") val measure15: String? = null,
    var isFavourite: Boolean = false,

) {
    @Ignore var isCocktailOfTheDay: Boolean = false
}