package com.adrianbucayan.myrecipeapp.domain.request

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

/**
 * @author Adrian Bucayan
 */
data class GetRecipeRequest(

    var token: String? = null,
    var id: Int? = null,

) : Serializable