package com.pompouslypickled.venison.api.Hit.DbMappers

import com.pompouslypickled.venison.api.Hit.DisplayedRecipe
import com.pompouslypickled.venison.api.Hit.RecipeBook

class RecipeBookToDisplayedRecipiesMapper {


    fun toDisplayedRecipe(recipeBookentry: RecipeBook) : DisplayedRecipe {
        return DisplayedRecipe(
            recipeBookentry.bookName,
            recipeBookentry.bookCalories,
            recipeBookentry.bookImage,
            recipeBookentry.bookTotal_time,
            recipeBookentry.bookYield,
            recipeBookentry.bookUrl,
            recipeBookentry.bookIngredients,
            recipeBookentry.bookUri,
            recipeBookentry.listOfIngredients
        )



    }
}