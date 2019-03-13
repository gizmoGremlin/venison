package com.pompouslypickled.venison.adapters

import android.view.ViewGroup
import com.pompouslypickled.venison.api.Hit.RecipeBook
import com.pompouslypickled.venison.api.Hit.StoredRecipes
import com.pompouslypickled.venison.common.RecyclerViewAdapterBase
import com.pompouslypickled.venison.common.ViewWrapper

class RecipeBookListAdapter() : RecyclerViewAdapterBase<StoredRecipes, RecipeBookItemView>() {
    var onClickAction:  ((v :RecipeBookItemView,  myrecipe : RecipeBook) ->Unit )? = null

    override fun onBindViewHolder(holder: ViewWrapper<RecipeBookItemView>, position: Int) {
        val recipe =  items[position]

        holder.view.apply {
            bindRecipeBookItem(recipe)
        }

    }

    override fun onCreateItemView(parent: ViewGroup, viewType: Int): RecipeBookItemView  =
        RecipeBookItemView(parent.context)

}