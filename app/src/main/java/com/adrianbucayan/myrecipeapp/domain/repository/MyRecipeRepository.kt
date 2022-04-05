package com.adrianbucayan.myrecipeapp.domain.repository

import com.adrianbucayan.myrecipeapp.data.remote.dto.RecipeDto
import com.adrianbucayan.myrecipeapp.data.remote.dto.RecipeSearchDto
import com.adrianbucayan.myrecipeapp.domain.request.GetRecipeRequest
import com.adrianbucayan.myrecipeapp.domain.request.SearchRecipeRequest


interface MyRecipeRepository {

    suspend fun get(getRecipeRequest: GetRecipeRequest): RecipeDto

    suspend fun search(searchRecipeRequest: SearchRecipeRequest): RecipeSearchDto

}