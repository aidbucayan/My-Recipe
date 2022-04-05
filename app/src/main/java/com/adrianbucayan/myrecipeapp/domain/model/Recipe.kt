package com.adrianbucayan.myrecipeapp.domain.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Recipe(
    var pk: Int?,
    var title: String? = "",
    val publisher : String? = "",
    val featuredImage : String? = "",
    val rating : Int?,
    val sourceUrl: String? = "",
    val ingredients: List<String> = emptyList(),
    val dateAdded: String? = "",
    val dateUpdated: String? = ""
)
