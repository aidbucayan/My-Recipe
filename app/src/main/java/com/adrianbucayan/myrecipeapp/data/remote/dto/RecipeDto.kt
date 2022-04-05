package com.adrianbucayan.myrecipeapp.data.remote.dto

import com.adrianbucayan.myrecipeapp.domain.model.Recipe
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class RecipeDto(

    @JsonProperty("pk")
    var pk: Int?,

    @JsonProperty("title")
    val title : String? = "",

    @JsonProperty("publisher")
    val publisher : String? = "",

    @JsonProperty("featured_image")
    val featuredImage : String? = "",

    @JsonProperty("rating")
    val rating : Int?,

    @JsonProperty("source_url")
    val sourceUrl: String? = "",

    @JsonProperty("ingredients")
    val ingredients: List<String> = emptyList(),

    @JsonProperty("date_added")
    val dateAdded: String? = "",

    @JsonProperty("date_updated")
    val dateUpdated: String? = "",
)

fun RecipeDto.toRecipe(): Recipe {
    return Recipe(
        pk = pk,
        title = title,
        publisher = publisher,
        featuredImage = featuredImage,
        rating = rating,
        sourceUrl = sourceUrl,
        ingredients = ingredients,
        dateAdded = dateAdded,
        dateUpdated = dateUpdated,
    )
}



