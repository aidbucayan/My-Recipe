package com.adrianbucayan.myrecipeapp.domain.request


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
data class SearchRecipeRequest(

    var token: String? = null,
    var page: Int? = null,
    var query: String? = null,

) : Serializable