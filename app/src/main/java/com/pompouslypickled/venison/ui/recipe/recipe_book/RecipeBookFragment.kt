package com.pompouslypickled.venison.ui.recipe.recipe_book

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.pompouslypickled.venison.R
import com.pompouslypickled.venison.adapters.RecipeBookItemView
import com.pompouslypickled.venison.adapters.RecipeBookListAdapter
import com.pompouslypickled.venison.api.Hit.RecipeBook
import com.pompouslypickled.venison.api.Hit.StoredRecipes
import com.pompouslypickled.venison.extentions.visible
import com.pompouslypickled.venison.ui.MainActivity
import kotlinx.android.synthetic.main.recipe_book_fragment.*

class RecipeBookFragment : Fragment() {


    val mainActivity: MainActivity
        get() = activity as MainActivity

    private lateinit var recipeBookAdapter: RecipeBookListAdapter
    private var viewModel: RecipeBookViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.recipe_book_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = RecipeBookViewModel.getBookViewModelInstance(activity!!)
        initRecipeBookList()
        initObservers()
        viewModel?.loadRecipesFromBook()
    }

    private fun initObservers() {
        viewModel?.isDataLoading()?.observe(this, Observer {
            it.let {
                book_recipe_progressBar.visible(it)
            }
        })

        viewModel?.getRecipies()?.observe(this, Observer {
            it?.let {
                showRecipeBookRecipes(it)
            }
        })
        viewModel?.isNetworkError()?.observe(this, Observer {
            it?.let {
                if (it) {
                    Toast.makeText(activity, "Network Error!!!", Toast.LENGTH_SHORT)


                }
            }
        })
        viewModel?.isUnknownError()?.observe(this, Observer {
            it?.let {
                if (it) {
                    Toast.makeText(activity, "Unknown Error!!!", Toast.LENGTH_SHORT)

                }
            }
        })
    }

    private fun showRecipeBookRecipes(theRecipeBook: List<StoredRecipes>) {
        if (recipeBookAdapter.items.isEmpty()) {
            recipeBookAdapter.setItems(theRecipeBook)

        } else {
            recipeBookAdapter.addMoreItems(theRecipeBook)
        }
    }

    private fun initRecipeBookList() {
        recipeBookAdapter = RecipeBookListAdapter().apply {

            onClickAction = { v: RecipeBookItemView, myrecipe: RecipeBook -> }
        }
        val mLayoutManager = LinearLayoutManager(
            context, RecyclerView.VERTICAL, false
        )

        recipe_book_recycler_view.apply {
            adapter = recipeBookAdapter
            setHasFixedSize(true)
            layoutManager = mLayoutManager

        }


    }
}
