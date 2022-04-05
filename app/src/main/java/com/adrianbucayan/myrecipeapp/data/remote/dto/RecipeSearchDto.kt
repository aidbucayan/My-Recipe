package com.adrianbucayan.myrecipeapp.data.remote.dto

import com.adrianbucayan.myrecipeapp.domain.model.RecipeSearch
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class RecipeSearchDto(

    @JsonProperty("count")
    var count : Int?,

    @JsonProperty("results")
    var recipes: List<RecipeDto>,

)

fun RecipeSearchDto.toRecipeSearch(): RecipeSearch {
    return RecipeSearch(
        count = count,
        recipes = recipes.map { it.toRecipe() },
    )
}



