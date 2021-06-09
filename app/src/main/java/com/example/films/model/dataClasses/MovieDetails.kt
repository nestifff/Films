package com.example.films.model.dataClasses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

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
    var budget: Int,
    var revenue: Int,
    var tagline: String,
    var runtime: Int,
    var releaseDate: String,
    var homepage: String,
    var actors: List<Actor>

) {

    override fun toString(): String {
        return "MovieDetails(id=$id, age=$age, title='$title', genres=$genres, reviewCount=$reviewCount, isLiked=$isLiked, rating=$rating, posterImageUrl='$posterImageUrl', backgroundImageUrl='$backgroundImageUrl', storyLine='$storyLine', budget=$budget, revenue=$revenue, tagline='$tagline', runtime=$runtime, releaseDate='$releaseDate', homepage='$homepage', actors=$actors)"
    }
}