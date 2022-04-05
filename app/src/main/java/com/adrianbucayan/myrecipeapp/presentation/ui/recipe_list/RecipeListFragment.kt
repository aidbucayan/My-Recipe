package com.adrianbucayan.myrecipeapp.presentation.ui.recipe_list

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adrianbucayan.myrecipeapp.R
import com.adrianbucayan.myrecipeapp.common.Constants
import com.adrianbucayan.myrecipeapp.common.Constants.PAGE_SIZE
import com.adrianbucayan.myrecipeapp.common.Resource
import com.adrianbucayan.myrecipeapp.databinding.FragmentRecipeListBinding
import com.adrianbucayan.myrecipeapp.domain.model.Recipe
import com.adrianbucayan.myrecipeapp.domain.model.RecipeSearch
import com.adrianbucayan.myrecipeapp.domain.request.SearchRecipeRequest
import com.adrianbucayan.myrecipeapp.presentation.util.Utils
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
    @Inject
    lateinit var utils: Utils
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

        binding.recipeListSearch.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                utils.hideKeyboard(requireActivity(), v)
                page = 1
                apiCall(page, query = binding.recipeListSearch.text.toString())
                return@OnEditorActionListener true
            }
            false
        })
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

                    if (binding.shimmerViewContainer.visibility == View.GONE
                        && lastVisibleItemPosition == listItemIndex
                        && limitOffset < totalItemCount) {
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
        val navController: NavController =
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main)
        val bundle = Bundle()
        bundle.putParcelable(Constants.RECIPE, recipe)
        navController.navigate(R.id.action_recipeListFragment_to_recipeFragment, bundle)
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

    private fun apiCall(mPage: Int?, query: String) {
        recipeViewModel.setGetRecipeEvent(
            GetRecipeEvent.GetGetRecipeEvents,
            SearchRecipeRequest(token,
                mPage,
                query
            )
        )
    }

    @SuppressLint("TimberArgCount")
    private fun observers() {
        recipeViewModel.dataStateGetRecipe.observe(viewLifecycleOwner) { dataStateGetRecipe ->
            when (dataStateGetRecipe) {

                is Resource.Success<RecipeSearch> -> {
                    Timber.e("dataStateGetRecipe SUCCESS")
                    displayShimmer(false)
                    totalItemCount = dataStateGetRecipe.data?.count!!
                    dataStateGetRecipe.data.recipes?.let { recipeListAdapter!!.submitList(it) }
                }

                is Resource.Error -> {
                    Timber.e("dataStateGetRecipe ERROR %s", dataStateGetRecipe.message)
                    displayShimmer(false)
                }

                is Resource.Loading -> {
                    Timber.e("dataStateGetRecipe LOADING")
                    displayShimmer(true)
                }
            }
        }
    }

    private fun displayShimmer(isDisplayed: Boolean) {
        if (isDisplayed) {
            binding.shimmerViewContainer.visibility = View.VISIBLE
            binding.shimmerViewContainer.startShimmer()
        }
        else {
            if(binding.recipeListRecyclerSwipe.isRefreshing)
                binding.recipeListRecyclerSwipe.isRefreshing = false
            binding.shimmerViewContainer.stopShimmer()
            binding.shimmerViewContainer.visibility = View.GONE
        }
    }


}