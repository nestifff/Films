package com.example.films

import android.content.Context
import java.io.Serializable

data class Movie(

    val name: String,
    val ageLimit: Int,
    val genre: String,
    var rating: Int,
    var numOfReviews: Int,
    val posterImageRecourse: Int,
    val length: Int,
    val isLiked: Boolean,
    val pictureTopBackgroundResource: Int,
    val storyline: String,
    val actors: List<Actor>
) : Serializable

fun generateMovies(context: Context): List<Movie> {

    val list: MutableList<Movie> = mutableListOf()
    list.add(
        Movie(
            name = "Avengers: End Game",
            ageLimit = 13,
            genre = "Action, Adventure, Drama",
            rating = 4,
            numOfReviews = 125,
            posterImageRecourse = R.drawable.movies_list_movie_poster_avengers_end_game,
            length = 137,
            isLiked = false,
            pictureTopBackgroundResource = 0,
            storyline = context.getString(R.string.storyline_avengers_end_game),
            actors = listOf(
                Actor("Robert Downey Jr.", R.drawable.artist_photo_downey_jr),
                Actor("Chris Evans", R.drawable.artist_photo_evans),
                Actor("Mark Ruffalo", R.drawable.artist_photo_ruffalo),
                Actor("Chris Hemsworth", R.drawable.artist_photo_hemsworth),
                Actor("Robert Downey Jr.", R.drawable.artist_photo_downey_jr),
                Actor("Chris Evans", R.drawable.artist_photo_evans),
                Actor("Mark Ruffalo", R.drawable.artist_photo_ruffalo),
                Actor("Chris Hemsworth", R.drawable.artist_photo_hemsworth)
            )
        )
    )
    list.add(
        Movie(
            name = "Tenet",
            ageLimit = 16,
            genre = "Action, Sci-Fi, Thriller",
            rating = 5,
            numOfReviews = 98,
            posterImageRecourse = R.drawable.movies_list_movie_poster_tenet,
            length = 97,
            isLiked = true,
            pictureTopBackgroundResource = 0,
            storyline = context.getString(R.string.storyline_tenet),
            actors = listOf(
                Actor("Robert Downey Jr.", R.drawable.artist_photo_downey_jr),
                Actor("Chris Evans", R.drawable.artist_photo_evans),
                Actor("Mark Ruffalo", R.drawable.artist_photo_ruffalo),
                Actor("Chris Hemsworth", R.drawable.artist_photo_hemsworth),
                Actor("Robert Downey Jr.", R.drawable.artist_photo_downey_jr),
                Actor("Chris Evans", R.drawable.artist_photo_evans),
                Actor("Mark Ruffalo", R.drawable.artist_photo_ruffalo),
                Actor("Chris Hemsworth", R.drawable.artist_photo_hemsworth)
            )
        )
    )

    list.add(
        Movie(
            name = "Black Widow",
            ageLimit = 13,
            genre = "Action, Adventure, Sci-Fi",
            rating = 4,
            numOfReviews = 38,
            posterImageRecourse = R.drawable.movies_list_movie_poster_black_widow,
            length = 102,
            isLiked = false,
            pictureTopBackgroundResource = 0,
            storyline = context.getString(R.string.storyline_black_widow),
            actors = listOf(
                Actor("Robert Downey Jr.", R.drawable.artist_photo_downey_jr),
                Actor("Chris Evans", R.drawable.artist_photo_evans),
                Actor("Mark Ruffalo", R.drawable.artist_photo_ruffalo),
                Actor("Chris Hemsworth", R.drawable.artist_photo_hemsworth),
                Actor("Robert Downey Jr.", R.drawable.artist_photo_downey_jr),
                Actor("Chris Evans", R.drawable.artist_photo_evans),
                Actor("Mark Ruffalo", R.drawable.artist_photo_ruffalo),
                Actor("Chris Hemsworth", R.drawable.artist_photo_hemsworth)
            )
        )
    )
    list.add(
        Movie(
            name = "Wonder Woman 1984",
            ageLimit = 13,
            genre = "Action, Adventure, Fantasy",
            rating = 5,
            numOfReviews = 74,
            posterImageRecourse = R.drawable.movies_list_movie_poster_wonderful_woman_1984,
            length = 120,
            isLiked = false,
            pictureTopBackgroundResource = 0,
            storyline = context.getString(R.string.storyline_wonder_woman_1984),
            actors = listOf(
                Actor("Robert Downey Jr.", R.drawable.artist_photo_downey_jr),
                Actor("Chris Evans", R.drawable.artist_photo_evans),
                Actor("Mark Ruffalo", R.drawable.artist_photo_ruffalo),
                Actor("Chris Hemsworth", R.drawable.artist_photo_hemsworth),
                Actor("Robert Downey Jr.", R.drawable.artist_photo_downey_jr),
                Actor("Chris Evans", R.drawable.artist_photo_evans),
                Actor("Mark Ruffalo", R.drawable.artist_photo_ruffalo),
                Actor("Chris Hemsworth", R.drawable.artist_photo_hemsworth)
            )
        )
    )
    list.add(
        Movie(
            name = "Avengers: End Game",
            ageLimit = 13,
            genre = "Action, Adventure, Drama",
            rating = 4,
            numOfReviews = 125,
            posterImageRecourse = R.drawable.movies_list_movie_poster_avengers_end_game,
            length = 137,
            isLiked = false,
            pictureTopBackgroundResource = 0,
            storyline = context.getString(R.string.storyline_avengers_end_game),
            actors = listOf(
                Actor("Robert Downey Jr.", R.drawable.artist_photo_downey_jr),
                Actor("Chris Evans", R.drawable.artist_photo_evans),
                Actor("Mark Ruffalo", R.drawable.artist_photo_ruffalo),
                Actor("Chris Hemsworth", R.drawable.artist_photo_hemsworth),
                Actor("Robert Downey Jr.", R.drawable.artist_photo_downey_jr),
                Actor("Chris Evans", R.drawable.artist_photo_evans),
                Actor("Mark Ruffalo", R.drawable.artist_photo_ruffalo),
                Actor("Chris Hemsworth", R.drawable.artist_photo_hemsworth)
            )
        )
    )

    return list
}