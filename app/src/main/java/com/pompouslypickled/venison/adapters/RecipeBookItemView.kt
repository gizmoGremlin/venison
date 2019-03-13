package com.pompouslypickled.venison.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.pompouslypickled.venison.R
import com.pompouslypickled.venison.api.Hit.StoredRecipes
import kotlinx.android.synthetic.main.recipe_book_item_view.view.*

class RecipeBookItemView constructor( context: Context) : RelativeLayout(context) {

    init {
        LayoutInflater.from(context).inflate(R.layout.recipe_book_item_view, this, true)
    }

    fun bindRecipeBookItem(recipe: StoredRecipes){
        Glide.with(context)
            .load(Uri.parse(recipe.stored_bookImage))
            .into(recipe_book_image_view)
        recipe_book_title.text = recipe.stored_bookName

    }
}