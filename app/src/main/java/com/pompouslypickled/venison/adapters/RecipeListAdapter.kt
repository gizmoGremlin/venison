package com.pompouslypickled.venison.adapters

import android.view.ViewGroup
import com.pompouslypickled.venison.api.Hit.DisplayedRecipe
import com.pompouslypickled.venison.common.RecyclerViewAdapterBase
import com.pompouslypickled.venison.common.ViewWrapper

class RecipeListAdapter() : RecyclerViewAdapterBase<DisplayedRecipe, RecipeItemView>(){
       var onClickAction3:  ((v :RecipeItemView ,  myrecipe :DisplayedRecipe) ->Unit )? = null
        var onClickAction2 : (( v :RecipeItemView) -> Unit)? = null
            var onClickAction: (( myrecipe :DisplayedRecipe) -> Unit)? = null



    override fun onCreateItemView(parent: ViewGroup, viewType: Int): RecipeItemView  =
        RecipeItemView(parent.context)

    override fun onBindViewHolder(holder: ViewWrapper<RecipeItemView>, position: Int) {
        val recipe =  items[position]

        holder.view.apply {
            bind(recipe)
        }
        holder.view.setOnClickListener {
            onClickAction2?.invoke(it as RecipeItemView)
            onClickAction?.invoke(items[position])
            onClickAction3?.invoke(it as @kotlin.ParameterName(name = "v") RecipeItemView, items[position])
        }
    }


}


