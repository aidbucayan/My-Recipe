package com.adrianbucayan.myrecipeapp.domain.use_case

import com.adrianbucayan.myrecipeapp.common.Resource
import com.adrianbucayan.myrecipeapp.data.remote.dto.toRecipe
import com.adrianbucayan.myrecipeapp.domain.model.Recipe
import com.adrianbucayan.myrecipeapp.domain.repository.MyRecipeRepository
import com.adrianbucayan.myrecipeapp.domain.request.GetRecipeRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRecipeUseCase @Inject constructor(private val repository: MyRecipeRepository) {

    operator fun invoke(getRecipeRequest: GetRecipeRequest): Flow<Resource<Recipe>> = flow {
        try {
            emit(Resource.Loading<Recipe>())
            val getRecipe = repository.get(getRecipeRequest).toRecipe()
            emit(Resource.Success<Recipe>(getRecipe))
        } catch(e: HttpException) {
            emit(Resource.Error<Recipe>(e.localizedMessage))
        } catch(e: IOException) {
            emit(Resource.Error<Recipe>(e.localizedMessage))
        }
    }

}