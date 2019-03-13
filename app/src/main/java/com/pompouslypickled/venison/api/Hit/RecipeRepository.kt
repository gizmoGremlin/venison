package com.pompouslypickled.venison.api.Hit

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.pompouslypickled.venison.api.Hit.DbMappers.DisplayRecipeToRecipeBookMapper
import com.pompouslypickled.venison.api.Hit.DbMappers.RecipeBookToDisplayedRecipiesMapper
import io.reactivex.Observable

class RecipeRepository(val api: RecipeApi, val dao: RecipeDao) {



    fun getRecipes(query: String, from: Int = 1) : Observable<RecipeData> {

            return api.searchRecipies(query,"c3f86cd2","4f3347598c67bddaab8183119f5fcc61",from,from + 6)

    }

    fun getASingleRecipe(recipeName: String) : Observable<RecipeBook> {

      return dao.getRecipe(recipeName)
    }
    @WorkerThread
    suspend fun insertSingleRecipe(singleRecipe: DisplayedRecipe){
        dao.insertSingle(DisplayRecipeToRecipeBookMapper().toRecipeBook(singleRecipe))
    }
    fun getAllRecipesInBook(): Observable<List<RecipeBook>>{
        return dao.getAllRecipesInBook()
    }
    @WorkerThread
    suspend fun  insertSingleStoredRecipe(storedRecipes: StoredRecipes){
        dao.insertSingleStoredRecipe(storedRecipes)
    }
    fun getAllStoredRecipes():Observable<List<StoredRecipes>>{
        return dao.getAllStoredRecipes()
    }
}