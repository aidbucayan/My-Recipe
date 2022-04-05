package com.adrianbucayan.myrecipeapp.data.remote

import com.adrianbucayan.myrecipeapp.data.remote.dto.RecipeDto
import com.adrianbucayan.myrecipeapp.data.remote.dto.RecipeSearchDto
import retrofit2.http.*

interface MyRecipeApi {

    @GET("get")
    suspend fun get(@Header("Authorization") token: String, @Query("id") id: Int): RecipeDto

    @GET("search")
    suspend fun search(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("query") query: String
    ): RecipeSearchDto

}