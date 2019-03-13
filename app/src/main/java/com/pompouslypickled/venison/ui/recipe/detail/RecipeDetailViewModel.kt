package com.pompouslypickled.venison.ui.recipe.detail

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.pompouslypickled.venison.VenisonApp
import com.pompouslypickled.venison.api.Hit.DbMappers.DisplayedRecipeToStoredRecipe
import com.pompouslypickled.venison.api.Hit.DbMappers.RecipeBookToDisplayedRecipiesMapper
import com.pompouslypickled.venison.api.Hit.DisplayedRecipe
import com.pompouslypickled.venison.api.Hit.RecipeBook
import com.pompouslypickled.venison.ui.recipe.recipe_list.RecipeListViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RecipeDetailViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    companion object {
        private var INSTANCE: RecipeDetailViewModel? = null

        private val recipeRepository = VenisonApp.injectRecipeRepo()

        fun getInstance(activity: FragmentActivity): RecipeDetailViewModel? {
            if (INSTANCE == null) {
                INSTANCE = ViewModelProviders.of(activity).get(RecipeDetailViewModel::class.java)
            }
            return INSTANCE
        }
    }
///coroutines

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private var liveDetailRecipe: MutableLiveData<DisplayedRecipe> = MutableLiveData()
    private var liveRecipeForBook: MutableLiveData<RecipeBook> = MutableLiveData()

    fun getRecipeEntryForRecipeBook(): LiveData<RecipeBook> = liveRecipeForBook
    fun getDetailRecipe(): LiveData<DisplayedRecipe> = liveDetailRecipe

    fun loadSingleRecipe(recipeArgs: String) {
        recipeRepository.getASingleRecipe(recipeArgs)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { recipeBook ->
                    liveDetailRecipe.postValue(RecipeBookToDisplayedRecipiesMapper().toDisplayedRecipe(recipeBook))
                },
                { error ->
                    error.printStackTrace()
                }
            )

    }

    fun persistASingleRecipeToRecipeBook(displayedRecipe: DisplayedRecipe) = scope.launch(Dispatchers.IO){
        recipeRepository.insertSingleStoredRecipe(DisplayedRecipeToStoredRecipe().toStoredRecipe(displayedRecipe))
    }


    fun addSingleRecipeToBook(singleRecipe: DisplayedRecipe){
        recipeRepository.getASingleRecipe(singleRecipe.name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { recipeBook ->
                recipeBook.isInMyRecipies = true
                liveRecipeForBook.postValue(recipeBook)




            }

    }
}