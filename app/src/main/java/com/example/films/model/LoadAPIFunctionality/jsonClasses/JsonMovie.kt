package com.android.academy.fundamentals.homework.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class JsonMovie(
    val id: Int,
    val title: String,
    @SerialName("poster_path")
    val posterPicture: String?,
    @SerialName("backdrop_path")
    val backdropPicture: String?,
    @SerialName("genre_ids")
    val genreIds: List<Int>?,
    @SerialName("vote_average")
    val ratings: Float?,
    @SerialName("vote_count")
    val votesCount: Int?,
    val overview: String?,
    val adult: Boolean?
)