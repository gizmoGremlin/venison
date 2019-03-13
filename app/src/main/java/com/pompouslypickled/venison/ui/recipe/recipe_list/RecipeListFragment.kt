package com.pompouslypickled.venison.ui.recipe.recipe_list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pompouslypickled.venison.BuildConfig

import com.pompouslypickled.venison.R
import com.pompouslypickled.venison.VenisonApp
import com.pompouslypickled.venison.adapters.RecipeListAdapter
import com.pompouslypickled.venison.api.Hit.DisplayedRecipe
import com.pompouslypickled.venison.extentions.visible
import com.pompouslypickled.venison.provider.GameTypeProviderImpl
import com.pompouslypickled.venison.ui.MainActivity
import kotlinx.android.synthetic.main.recipe_list_fragment.*
import org.jetbrains.anko.support.v4.onRefresh

class RecipeListFragment : Fragment()   {


    val mainActivity: MainActivity
        get() = activity as MainActivity

    private lateinit var recipeAdapter : RecipeListAdapter

    private var listViewModel: RecipeListViewModel? =null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.recipe_list_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         listViewModel = RecipeListViewModel.getInstance(activity!!)

        initRecipeList()

        initSwipeToRefresh()

        initObservers()

        listViewModel?.loadRecipies()


    }

    override fun onResume() {
        super.onResume()
        var gameTypeProvider = GameTypeProviderImpl(VenisonApp.applicationContext())
        val query = gameTypeProvider.getGameTypeEnum().name
        Log.d("Here iS The Query", query)
        //listViewModel?.resetCurrentPage()
        query?.let { listViewModel?.setQuery(it) }
        recipeAdapter.clearItems()

        listViewModel?.loadRecipies()


    }
    private fun initObservers() {
        listViewModel?.isDataLoading()?.observe(this, Observer {
            it.let {
                if (!swipe_refresh.isRefreshing){
                    progressBar.visible(it)
                }
            }
        })

        listViewModel?.getRecipies()?.observe(this,Observer {
            it?.let {
                showRecipies(it)
            }
        })
        listViewModel?.isNetworkError()?.observe(this, Observer {
            it?.let {
                if (it) {
                    Toast.makeText(mainActivity,"Network Error!!!",Toast.LENGTH_SHORT)

                    hideRefreshingIcon()
                }
            }
        })
        listViewModel?.isUnknownError()?.observe(this, Observer {
            it?.let {
                if (it) {
                    Toast.makeText(mainActivity,"Unknown Error!!!",Toast.LENGTH_SHORT)
                    hideRefreshingIcon()
                }
            }
        })
    }

    private fun hideRefreshingIcon() {
        if (swipe_refresh.isRefreshing) {
            swipe_refresh.isRefreshing = false
            BuildConfig.APPLICATION_ID
        }
    }

    private fun showRecipies(recipies: List<DisplayedRecipe>) {
            if (swipe_refresh.isRefreshing){
                recipeAdapter.clearItems()
                swipe_refresh.isRefreshing = false
            }
            recipeAdapter.addMoreItems(recipies)
            if (recipies.isEmpty()){
                Toast.makeText(mainActivity,"No recipies Available!!!",Toast.LENGTH_SHORT)
            }
    }

    private fun initSwipeToRefresh() {
        swipe_refresh.onRefresh {

            listViewModel?.resetCurrentPage()
            listViewModel?.loadRecipies()
        }
    }

    private fun initRecipeList() {
        recipeAdapter = RecipeListAdapter().apply {
            //experiment..delete next 2 lines and on click action in recipelistadaptor
            val directions = RecipeListFragmentDirections.actionRecipeListFragmentToDetailFragment()

            //find out type of below
           onClickAction3 = {v, it ->

               val action = RecipeListFragmentDirections.actionRecipeListFragmentToDetailFragment(it.name)
               listViewModel?.persistSingleRecipe(it)
               //directions.arguments = it.name      //put in bundle??????
               v.findNavController().navigate(action)}
           /* onClickAction2 = { v -> v.findNavController().navigate(directions)  }
            onClickAction = {

                    it -> listViewModel?.persistSingleRecipe(it)





            }*/

        }
        val mLayoutManager = LinearLayoutManager(
            context, RecyclerView.VERTICAL, false)

        recycler_view.apply {
            adapter = recipeAdapter
            setHasFixedSize(true)
            layoutManager = mLayoutManager

            addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (isEndOfListAndNotLoading(mLayoutManager)) {
                        listViewModel?.loadRecipies()
                    }
                }
            })
        }

    }

    private fun isEndOfListAndNotLoading(layoutManager: LinearLayoutManager): Boolean {
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (!listViewModel!!.isDataLoading().value!! &&
            visibleItemCount + firstVisibleItemPosition >= totalItemCount
            && firstVisibleItemPosition >= 0) {
            return true
        }

        return false

    }


}




