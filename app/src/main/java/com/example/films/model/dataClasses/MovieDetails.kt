package com.example.films.model.dataClasses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class MovieDetails(

    var id: Int,
    var age: Int,
    var title: String,
    var genres: List<Genre>,
    var reviewCount: Int,
    var isLiked: Boolean,
    var rating: Float,
    var posterImageUrl: String,
    var backgroundImageUrl: String,
    var storyLine: String,
    var budget: Long,
    var revenue: Long,
    var tagline: String,
    var runtime: Int,
    var releaseDate: String,
    var homepage: String,
    var actors: List<Actor>

)