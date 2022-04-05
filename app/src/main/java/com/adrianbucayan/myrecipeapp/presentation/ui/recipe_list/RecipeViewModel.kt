package com.adrianbucayan.myrecipeapp.presentation.ui.recipe_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrianbucayan.myrecipeapp.common.Resource
import com.adrianbucayan.myrecipeapp.data.remote.dto.RecipeSearchDto
import com.adrianbucayan.myrecipeapp.domain.model.Recipe
import com.adrianbucayan.myrecipeapp.domain.model.RecipeSearch
import com.adrianbucayan.myrecipeapp.domain.request.GetRecipeRequest
import com.adrianbucayan.myrecipeapp.domain.request.SearchRecipeRequest
import com.adrianbucayan.myrecipeapp.domain.use_case.GetRecipeUseCase
import com.adrianbucayan.myrecipeapp.domain.use_case.SearchRecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val searchRecipeUseCase: SearchRecipeUseCase) : ViewModel() {

    private val _dataStateGetRecipe: MutableLiveData<Resource<RecipeSearch>> = MutableLiveData()

    val dataStateGetRecipe: LiveData<Resource<RecipeSearch>>
        get () = _dataStateGetRecipe

    fun setGetRecipeEvent(getRecipeEvent: GetRecipeEvent, request: SearchRecipeRequest) {
        viewModelScope.launch {
            when(getRecipeEvent) {
                is GetRecipeEvent.GetGetRecipeEvents -> {
                    searchRecipeUseCase(request)
                        .onEach { dataStateGetRecipe ->
                            _dataStateGetRecipe.value = dataStateGetRecipe
                        }
                        .launchIn(viewModelScope)
                }

                is GetRecipeEvent.None -> {

                }
            }
        }
    }

}

sealed class GetRecipeEvent {

    object GetGetRecipeEvents: GetRecipeEvent()

    object None: GetRecipeEvent()
}

