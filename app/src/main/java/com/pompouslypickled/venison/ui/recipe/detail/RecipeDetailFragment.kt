package com.pompouslypickled.venison.ui.recipe.detail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

import com.pompouslypickled.venison.R
import com.pompouslypickled.venison.api.Hit.DbMappers.RecipeBookToDisplayedRecipiesMapper
import com.pompouslypickled.venison.api.Hit.DisplayedRecipe
import kotlinx.android.synthetic.main.recipe_detail_fragment.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class RecipeDetailFragment : Fragment() {

    companion object {
        fun newInstance() = RecipeDetailFragment()
    }

    private lateinit var viewModel: RecipeDetailViewModel
    lateinit var detailRecipe: DisplayedRecipe
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.recipe_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RecipeDetailViewModel::class.java)
        // TODO: Use the ViewModel
        val test =titleText
        val args: RecipeDetailFragmentArgs by navArgs()
        val testValue = args.name
       // test.text = testValue
        viewModel.loadSingleRecipe(testValue)
        viewModel.getDetailRecipe().observe(this, Observer { it.let {

            detailRecipe = it
            bindDetailRecipe(it)
        } })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailFAB.setOnClickListener { v ->

            Snackbar.make(v,"added to your recipe book", Snackbar.LENGTH_SHORT).show()
            viewModel.addSingleRecipeToBook(detailRecipe)
            viewModel.getRecipeEntryForRecipeBook().observe(this,Observer{it ->
                viewModel.persistASingleRecipeToRecipeBook(RecipeBookToDisplayedRecipiesMapper().toDisplayedRecipe(it))
            })

        }

    }

    private fun bindDetailRecipe(recipe: DisplayedRecipe) {
        context?.let { Glide.with(it).load(recipe.image).into(detail_image) }

        titleText.text = recipe.name
        cook_time.text = "Time to prepare: " +recipe.total_time.toString()
        num_portions.text = "Portions: " + recipe.yield
        ingredient_list.text = recipe.ingredientList
        //directions.text = recipe.ingredients


    }

}
