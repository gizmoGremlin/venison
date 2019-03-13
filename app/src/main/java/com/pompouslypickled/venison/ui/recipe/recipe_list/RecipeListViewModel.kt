package com.pompouslypickled.venison.ui.recipe.recipe_list

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders
import com.pompouslypickled.venison.VenisonApp
import com.pompouslypickled.venison.api.Hit.DisplayedRecipe
import com.pompouslypickled.venison.api.Hit.ApiEntityToDisplayMapper.RecipeDataMapper
import com.pompouslypickled.venison.provider.GameTypeProviderImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import kotlin.coroutines.CoroutineContext

class RecipeListViewModel : ViewModel() {


    companion object {
        private var INSTANCE: RecipeListViewModel? = null

        private val recipeRepository = VenisonApp.injectRecipeRepo()

        fun getInstance(activity: FragmentActivity): RecipeListViewModel? {
            if (INSTANCE == null) {
                INSTANCE = ViewModelProviders.of(activity).get(RecipeListViewModel::class.java)
            }
            return INSTANCE
        }
    }
///coroutines

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)



    ///
    private var gameTypeProvider = GameTypeProviderImpl(VenisonApp.applicationContext())
    private var isDataLoading: MutableLiveData<Boolean> = MutableLiveData()
    private var recipies: MutableLiveData<List<DisplayedRecipe>> = MutableLiveData()
    private var isNetworkError: MutableLiveData<Boolean> = MutableLiveData()
    private var isUnknownError: MutableLiveData<Boolean> = MutableLiveData()

    private var totalPages: Int = 0
    private var currentPage: Int = 1
    private var query: String = gameTypeProvider.getGameTypeEnum().name
    fun getQuery(): String {
        return query
    }

    fun resetCurrentPage() {
        currentPage = 1
    }

    fun setQuery(query: String) {
        this.query = query
    }

    fun isDataLoading(): LiveData<Boolean> = isDataLoading

    fun getRecipies(): LiveData<List<DisplayedRecipe>> = recipies

    fun isNetworkError(): LiveData<Boolean> = isNetworkError

    fun isUnknownError(): LiveData<Boolean> = isUnknownError

    fun loadRecipies() {

        isDataLoading.postValue(true)

        recipeRepository.getRecipes(query, currentPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    totalPages = result.to
                    if (currentPage < totalPages) {
                        currentPage += 1
                        recipies.postValue(RecipeDataMapper().toDisplayedRecipeList(result))
                    }
                    isDataLoading.postValue(false)
                },
                { error ->
                    error.printStackTrace()
                    isDataLoading.postValue(false)
                    when (error) {
                        is UnknownHostException -> isNetworkError.postValue(true)
                        else -> isUnknownError.postValue(true)
                    }
                }
            )


    }
    fun persistSingleRecipe(singleRecipe: DisplayedRecipe) =scope.launch(Dispatchers.IO) {

        recipeRepository.insertSingleRecipe(singleRecipe)
    }
    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

}
