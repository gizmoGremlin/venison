package com.pompouslypickled.venison.api.Hit.DbMappers

import com.pompouslypickled.venison.api.Hit.DisplayedRecipe
import com.pompouslypickled.venison.api.Hit.RecipeBook

class DisplayRecipeToRecipeBookMapper{

    fun toRecipeBook(displayedRecipe: DisplayedRecipe) :RecipeBook{
        return RecipeBook(
            displayedRecipe.name,
            displayedRecipe.calories,
            displayedRecipe.image,
            displayedRecipe.total_time,
            displayedRecipe.yield,
            displayedRecipe.url,
            displayedRecipe.ingredients,
            displayedRecipe.uri,
            false,
            displayedRecipe.ingredientList


        )
    }


}