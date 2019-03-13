package com.pompouslypickled.venison.api.Hit.ApiEntityToDisplayMapper

import com.pompouslypickled.venison.api.Hit.DisplayedRecipe
import com.pompouslypickled.venison.api.Hit.Hit
import kotlin.math.truncate

class RecipeToDisplayRecipeMapper {

    fun toDisplayedRecipe(hit : Hit) : DisplayedRecipe {
        return DisplayedRecipe(
            hit.recipe.label,
            hit.recipe.calories,
            hit.recipe.image,
            truncate(hit.recipe.totalTime),
            hit.recipe.yield,
            hit.recipe.url,
            hit.recipe.ingredientLines.toString(),
            hit.recipe.uri,
            IngredientListToStringMapper().ingredientToString(hit.recipe.ingredients)
        )
    }
}