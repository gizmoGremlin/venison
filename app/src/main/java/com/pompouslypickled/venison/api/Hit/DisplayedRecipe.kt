package com.pompouslypickled.venison.api.Hit



data class DisplayedRecipe (

    val name: String,
    val calories: Double,
    val image: String,
    val total_time: Double,
    val yield: Double,
    val url: String,
    val ingredients: String,
    val uri: String,
    var ingredientList: String
)