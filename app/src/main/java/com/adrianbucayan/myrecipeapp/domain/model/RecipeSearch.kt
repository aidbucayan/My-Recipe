package com.adrianbucayan.myrecipeapp.domain.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class RecipeSearch(
    val count : Int?,
    var recipes: List<Recipe>? = null
)
