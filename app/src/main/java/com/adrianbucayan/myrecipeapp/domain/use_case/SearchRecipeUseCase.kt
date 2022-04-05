package com.adrianbucayan.myrecipeapp.domain.use_case

import com.adrianbucayan.myrecipeapp.common.Resource
import com.adrianbucayan.myrecipeapp.data.remote.dto.toRecipeSearch
import com.adrianbucayan.myrecipeapp.domain.model.RecipeSearch
import com.adrianbucayan.myrecipeapp.domain.repository.MyRecipeRepository
import com.adrianbucayan.myrecipeapp.domain.request.SearchRecipeRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchRecipeUseCase @Inject constructor(private val repository: MyRecipeRepository) {

    operator fun invoke(searchRecipeRequest: SearchRecipeRequest): Flow<Resource<RecipeSearch>> = flow {
        try {
            emit(Resource.Loading<RecipeSearch>())
            val getRecipe = repository.search(searchRecipeRequest).toRecipeSearch()
            emit(Resource.Success<RecipeSearch>(getRecipe))
        } catch(e: HttpException) {
            emit(Resource.Error<RecipeSearch>(e.localizedMessage))
        } catch(e: IOException) {
            emit(Resource.Error<RecipeSearch>(e.localizedMessage))
        }
    }

}