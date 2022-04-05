package com.adrianbucayan.myrecipeapp.presentation.ui.recipe_list

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adrianbucayan.myrecipeapp.R
import com.adrianbucayan.myrecipeapp.common.Constants.PAGE_SIZE
import com.adrianbucayan.myrecipeapp.common.Resource
import com.adrianbucayan.myrecipeapp.databinding.FragmentRecipeListBinding
import com.adrianbucayan.myrecipeapp.domain.model.Recipe
import com.adrianbucayan.myrecipeapp.domain.model.RecipeSearch
import com.adrianbucayan.myrecipeapp.domain.request.SearchRecipeRequest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RecipeListFragment : Fragment(R.layout.fragment_recipe_list) {

    private val recipeViewModel: RecipeViewModel by viewModels()
    private var _binding: FragmentRecipeListBinding? = null
    private val binding get() = _binding!!
    @Inject
    @Named("auth_token") lateinit var token: String
    private var recipeListAdapter : RecipeListAdapter? = null
    private var page : Int? = 1
    private var totalItemCount: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentRecipeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerview()
        apiCall(page)
        observers()
    }

    private fun initRecyclerview() {

        binding.recipeListRecycler.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)

            val itemDecoration: RecipeListAdapter.TopSpacingDecoration =
                RecipeListAdapter.TopSpacingDecoration(20)
            addItemDecoration(itemDecoration)

            recipeListAdapter = RecipeListAdapter()
            recipeListAdapter!!.toSelectRecipe = this@RecipeListFragment::toSelectRecipe
            adapter = recipeListAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                }

                @SuppressLint("TimberArgCount")
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val linearLayoutManager = layoutManager as LinearLayoutManager
                    val lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()
                    Timber.e("lastVisibleItemPosition = %s", lastVisibleItemPosition)
                    val listItemIndex = recipeListAdapter!!.getListItems().size - 1
                    Timber.e("listItemIndex = %s", listItemIndex)

                    var limitOffset = page?.times(PAGE_SIZE)
                    Timber.e("listItemIndex = %s", listItemIndex)
                    Timber.e("Page count * 30 < totalItemCount = ", (limitOffset!! < totalItemCount))
                    //binding.shimmerViewContainer.visibility == View.GONE &&
                    if (lastVisibleItemPosition == listItemIndex && limitOffset < totalItemCount) {
                        //displayLoadMoreProgressBar(true)
                        var newPage = page?.plus(1)
                        apiCall(newPage)
                    }
                }
            })
        }

        binding.recipeListRecyclerSwipe.setColorSchemeColors(resources.getColor(R.color.teal_200),
            resources.getColor(R.color.teal_200),
            resources.getColor(R.color.teal_200))

        binding.recipeListRecyclerSwipe.setOnRefreshListener { apiCall(page) }

    }

    private fun toSelectRecipe(recipe: Recipe, i: Int) {

    }

    private fun apiCall(mPage: Int?) {
        recipeViewModel.setGetRecipeEvent(
            GetRecipeEvent.GetGetRecipeEvents,
            SearchRecipeRequest(token,
                mPage,
                ""
            )
        )
    }

    @SuppressLint("TimberArgCount")
    private fun observers() {
        recipeViewModel.dataStateGetRecipe.observe(viewLifecycleOwner) { dataStateGetRecipe ->
            when (dataStateGetRecipe) {

                is Resource.Success<RecipeSearch> -> {
                    Timber.e("dataStateGetRecipe SUCCESS")
                    totalItemCount = dataStateGetRecipe.data?.count!!
                    dataStateGetRecipe.data.recipes?.let { recipeListAdapter!!.submitList(it) }
                }

                is Resource.Error -> {
                    Timber.e("dataStateGetRecipe ERROR %s", dataStateGetRecipe.message)

                }

                is Resource.Loading -> {
                    Timber.e("dataStateGetRecipe LOADING")
                }
            }
        }
    }


}