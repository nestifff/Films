package com.example.films.model.LoadAPIFunctionality.jsonClasses

import com.example.films.model.dataClasses.Genre
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
    val tagline: String,

    val id: Int,
    val title: String,
    @SerialName("poster_path")
    val posterPicture: String?,
    @SerialName("backdrop_path")
    val backdropPicture: String?,
    @SerialName("genres")
    val genres: List<Genre>,
    @SerialName("vote_average")
    val ratings: Float?,
    @SerialName("vote_count")
    val votesCount: Int?,
    val overview: String?,
    val adult: Boolean?

)