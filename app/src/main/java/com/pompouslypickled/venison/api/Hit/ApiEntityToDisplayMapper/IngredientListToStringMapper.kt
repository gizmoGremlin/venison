package com.pompouslypickled.venison.api.Hit.ApiEntityToDisplayMapper

import com.pompouslypickled.venison.api.Hit.Ingredient

class IngredientListToStringMapper {

    fun ingredientToString(ingredientList : List<Ingredient>): String {
        var ingredients = ""

        for (ingredient in ingredientList){
            ingredients += " ${ingredient.text}\n"
        }

        return ingredients
    }

}