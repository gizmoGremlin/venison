package com.pompouslypickled.venison.api.Hit





data class RecipeData(

    val recipeDataId: Int,
    val count: Int,
    val from: Int,
    val hits: List<Hit>,
    val more: Boolean,

    val q: String,
    val to: Int
)

data class Hit(

     val hitId: Int,
    val bookmarked: Boolean,
    val bought: Boolean,

    val recipe: Recipe
)

data class Recipe(

    val recipeId: Int,

    val calories: Double,




   val healthLabels: List<String>,

    val image: String,

    val ingredientLines: List<String>,

   val ingredients: List<Ingredient>,

    val label: String,

    val shareAs: String,

    val source: String,

    val totalTime: Double,

    val totalWeight: Double,
    val uri: String,

    val url: String,

    val yield: Double

)



data class Ingredient(

    val text: String,

    val weight: Double
)