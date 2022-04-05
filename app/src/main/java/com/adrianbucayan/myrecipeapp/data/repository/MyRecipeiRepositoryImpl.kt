package com.adrianbucayan.myrecipeapp.data.repository

import com.adrianbucayan.myrecipeapp.data.remote.MyRecipeApi
import com.adrianbucayan.myrecipeapp.data.remote.dto.RecipeDto
import com.adrianbucayan.myrecipeapp.data.remote.dto.RecipeSearchDto
import com.adrianbucayan.myrecipeapp.domain.repository.MyRecipeRepository
import com.adrianbucayan.myrecipeapp.domain.request.GetRecipeRequest
import com.adrianbucayan.myrecipeapp.domain.request.SearchRecipeRequest
import javax.inject.Inject

class MyRecipeRepositoryImpl @Inject constructor(private val api: MyRecipeApi) : MyRecipeRepository {

    override suspend fun get(getRecipeRequest: GetRecipeRequest): RecipeDto {
        return api.get(getRecipeRequest.token!!, getRecipeRequest.id!!)
    }

    override suspend fun search(searchRecipeRequest: SearchRecipeRequest): RecipeSearchDto {
        return api.search(searchRecipeRequest.token!!,
            searchRecipeRequest.page!!, searchRecipeRequest.query!!
        )
    }


}