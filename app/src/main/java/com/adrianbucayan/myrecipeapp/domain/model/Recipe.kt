package com.adrianbucayan.myrecipeapp.domain.model

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import kotlinx.parcelize.Parcelize

@JsonIgnoreProperties(ignoreUnknown = true)
@Parcelize
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

): Parcelable {

}
