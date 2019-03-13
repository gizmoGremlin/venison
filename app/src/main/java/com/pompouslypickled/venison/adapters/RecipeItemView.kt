package com.pompouslypickled.venison.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.pompouslypickled.venison.R
import com.pompouslypickled.venison.api.Hit.DisplayedRecipe
import kotlinx.android.synthetic.main.rc_item.view.*
import kotlin.math.truncate

class RecipeItemView constructor( context : Context ) : RelativeLayout(context) {

    init {
        LayoutInflater.from(context).inflate(R.layout.rc_item, this, true)
    }

    fun bind(recipe : DisplayedRecipe) {
        Glide.with(context)
            .load(Uri.parse(recipe.image))
            .into(recipe_pic)
        recipe_tit.text = recipe.name
        val temp: String
        if (recipe.calories < 1) temp = "not specified" else temp =truncate(recipe.calories).toString()
        cals.text = "calories: " +temp
        url.text = recipe.url
        time.text = "cooking time: " + recipe.total_time.toString() + " mins"
        ingredient_list.text =  recipe.ingredientList
        //the_yield.text = "Yield: " + recipe.yield.toString()
        //ingredients.text = "Ingredients: " + recipe.ingredients
    }

}