package com.pompouslypickled.venison.api.Hit

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Observable

@Dao
interface RecipeDao {



    @Query("SELECT * FROM recipe_book_table WHERE bookName = :recipeName")
    fun getRecipe(recipeName: String): Observable<RecipeBook>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSingle(recipeBook: RecipeBook)

    @Query("SELECT * FROM recipe_book_table WHERE isInMyRecipies = 1")
    fun getAllRecipesInBook(): Observable<List<RecipeBook>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSingleStoredRecipe(storedRecipes: StoredRecipes)

    @Query("SELECT * FROM stored_recipe_table")
    fun getAllStoredRecipes():Observable<List<StoredRecipes>>

}