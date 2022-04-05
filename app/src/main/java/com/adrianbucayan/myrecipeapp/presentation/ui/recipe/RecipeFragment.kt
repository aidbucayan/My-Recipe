package com.adrianbucayan.myrecipeapp.presentation.ui.recipe

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.adrianbucayan.myrecipeapp.R
import com.adrianbucayan.myrecipeapp.common.Constants
import com.adrianbucayan.myrecipeapp.databinding.FragmentRecipeBinding
import com.adrianbucayan.myrecipeapp.domain.model.Recipe
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RecipeFragment : Fragment(R.layout.fragment_recipe) {

    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding!!
    private var recipe: Recipe? = null
    private var recipeIngredientsListAdapter : RecipeIngredientsListAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recipe = arguments?.getParcelable(Constants.RECIPE)!!
        Timber.e("recipe = $recipe")

        binding.rowRecipeLastUpdate.text = recipe!!.dateUpdated + " by " + recipe!!.publisher
        binding.rowRecipeTitle.text =  recipe!!.title

        if(!recipe!!.featuredImage.isNullOrEmpty()) {
            Glide.with(binding.rowRecipeImage.context)
                .load(recipe!!.featuredImage)
                .fitCenter()
                .error(android.R.drawable.ic_menu_report_image)
                .into(binding.rowRecipeImage)
        }

        initRecyclerview(recipe!!.ingredients)
    }

    private fun initRecyclerview(ingredients: List<String>) {
        Timber.e("ingredients size %s", ingredients.size)
        binding.recipesRecycler.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)

            val itemDecoration: RecipeIngredientsListAdapter.TopSpacingDecoration =
                RecipeIngredientsListAdapter.TopSpacingDecoration(10)
            addItemDecoration(itemDecoration)

            recipeIngredientsListAdapter = RecipeIngredientsListAdapter()
            adapter = recipeIngredientsListAdapter
        }

        recipeIngredientsListAdapter!!.submitList(ingredients)

    }

}