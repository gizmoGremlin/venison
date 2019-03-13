package com.pompouslypickled.venison.api.Hit.ApiEntityToDisplayMapper

import com.pompouslypickled.venison.api.Hit.DisplayedRecipe
import com.pompouslypickled.venison.api.Hit.RecipeData

class RecipeDataMapper {

    fun toDisplayedRecipeList(recipeData : RecipeData) : List<DisplayedRecipe>{

        var displayedRecipeList : List<DisplayedRecipe> = emptyList()
        for (item in recipeData.hits) {
            displayedRecipeList = displayedRecipeList.plus(RecipeToDisplayRecipeMapper().toDisplayedRecipe(item))
        }
        return displayedRecipeList
    }

}