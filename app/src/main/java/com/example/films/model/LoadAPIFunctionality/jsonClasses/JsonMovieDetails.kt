package com.example.films.model.LoadAPIFunctionality.jsonClasses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class JsonMovieDetails(

    @SerialName("budget")
    val budget: Long,
    @SerialName("homepage")
    val homepage: String,
    @SerialName("release_date")
    val releaseDate: String,
    @SerialName("revenue")
    val revenue: Long,
    @SerialName("runtime")
    val runtime: Int,
    @SerialName("tagline")
    val tagline: String

)