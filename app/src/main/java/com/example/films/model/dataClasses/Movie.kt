package com.example.films.model.dataClasses

import com.example.films.model.database.movies.MovieForDB
import java.io.Serializable

data class Movie(
    var id: Int,
    var age: Int,
    var title: String,
    var genres: List<Genre>,
    var reviewCount: Int,
    var isLiked: Boolean,
    var rating: Float,
    var posterImageUrl: String,
    var backgroundImageUrl: String,
    var storyLine: String

) : Serializable {

    constructor(m: MovieForDB, genres: List<Genre>) :
            this(
                id = m.id,
                age = m.age,
                title = m.title,
                genres = ArrayList(genres),
                reviewCount = m.reviewCount,
                isLiked = m.isLiked,
                rating = m.rating,
                posterImageUrl = m.posterImageUrl,
                backgroundImageUrl = m.backgroundImageUrl,
                storyLine = m.storyLine
            )
}