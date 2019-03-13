package com.pompouslypickled.venison.ui.recipe.recipe_book

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders
import com.pompouslypickled.venison.VenisonApp

import com.pompouslypickled.venison.api.Hit.RecipeBook
import com.pompouslypickled.venison.api.Hit.StoredRecipes
import com.pompouslypickled.venison.ui.recipe.recipe_list.RecipeListViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException

class RecipeBookViewModel : ViewModel() {


    companion object {
        private var INSTANCE: RecipeBookViewModel? = null

        private val recipeRepository = VenisonApp.injectRecipeRepo()

        fun getBookViewModelInstance(activity: FragmentActivity): RecipeBookViewModel? {
            if (INSTANCE == null) {
                INSTANCE = ViewModelProviders.of(activity).get(RecipeBookViewModel::class.java)
            }
            return INSTANCE
        }
    }

    private var isDataLoading: MutableLiveData<Boolean> = MutableLiveData()
    private var recipies: MutableLiveData<List<StoredRecipes>> = MutableLiveData()
    private var isNetworkError: MutableLiveData<Boolean> = MutableLiveData()
    private var isUnknownError: MutableLiveData<Boolean> = MutableLiveData()

    fun isDataLoading(): LiveData<Boolean> = isDataLoading

    fun getRecipies(): LiveData<List<StoredRecipes>> = recipies

    fun isNetworkError(): LiveData<Boolean> = isNetworkError

    fun isUnknownError(): LiveData<Boolean> = isUnknownError

    fun loadRecipesFromBook(){
        isDataLoading.postValue(true)

        recipeRepository.getAllStoredRecipes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    result -> recipies.postValue(result)

                    isDataLoading.postValue(false)
                },
                { error -> error.printStackTrace()
                            isDataLoading.postValue(false)
                    when(error){
                        is UnknownHostException -> isNetworkError.postValue(true)
                        else -> isUnknownError.postValue(true)
                    }
                }
            )
    }
}
