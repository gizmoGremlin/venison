package com.pompouslypickled.venison.api.Hit.DbMappers

import com.pompouslypickled.venison.api.Hit.DisplayedRecipe
import com.pompouslypickled.venison.api.Hit.StoredRecipes

class DisplayedRecipeToStoredRecipe {

    fun toStoredRecipe(displayedRecipe: DisplayedRecipe): StoredRecipes {
        return StoredRecipes(displayedRecipe.name,
        displayedRecipe.calories,
        displayedRecipe.image,
        displayedRecipe.total_time,
        displayedRecipe.yield,
        displayedRecipe.url,
        displayedRecipe.ingredients,
        displayedRecipe.uri,
            true
        )
    }
}