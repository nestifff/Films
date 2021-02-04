package com.example.films

import android.content.Context
import java.io.Serializable

data class MovieForOldList(

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
    val actors: List<ActorForOldList>
) : Serializable

fun generateMovies(context: Context): List<MovieForOldList> {

    val list: MutableList<MovieForOldList> = mutableListOf()
    list.add(
        MovieForOldList(
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
                ActorForOldList("Robert Downey Jr.", R.drawable.artist_photo_downey_jr),
                ActorForOldList("Chris Evans", R.drawable.artist_photo_evans),
                ActorForOldList("Mark Ruffalo", R.drawable.artist_photo_ruffalo),
                ActorForOldList("Chris Hemsworth", R.drawable.artist_photo_hemsworth),
                ActorForOldList("Robert Downey Jr.", R.drawable.artist_photo_downey_jr),
                ActorForOldList("Chris Evans", R.drawable.artist_photo_evans),
                ActorForOldList("Mark Ruffalo", R.drawable.artist_photo_ruffalo),
                ActorForOldList("Chris Hemsworth", R.drawable.artist_photo_hemsworth)
            )
        )
    )
    list.add(
        MovieForOldList(
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
                ActorForOldList("Robert Downey Jr.", R.drawable.artist_photo_downey_jr),
                ActorForOldList("Chris Evans", R.drawable.artist_photo_evans),
                ActorForOldList("Mark Ruffalo", R.drawable.artist_photo_ruffalo),
                ActorForOldList("Chris Hemsworth", R.drawable.artist_photo_hemsworth),
                ActorForOldList("Robert Downey Jr.", R.drawable.artist_photo_downey_jr),
                ActorForOldList("Chris Evans", R.drawable.artist_photo_evans),
                ActorForOldList("Mark Ruffalo", R.drawable.artist_photo_ruffalo),
                ActorForOldList("Chris Hemsworth", R.drawable.artist_photo_hemsworth)
            )
        )
    )

    list.add(
        MovieForOldList(
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
                ActorForOldList("Robert Downey Jr.", R.drawable.artist_photo_downey_jr),
                ActorForOldList("Chris Evans", R.drawable.artist_photo_evans),
                ActorForOldList("Mark Ruffalo", R.drawable.artist_photo_ruffalo),
                ActorForOldList("Chris Hemsworth", R.drawable.artist_photo_hemsworth),
                ActorForOldList("Robert Downey Jr.", R.drawable.artist_photo_downey_jr),
                ActorForOldList("Chris Evans", R.drawable.artist_photo_evans),
                ActorForOldList("Mark Ruffalo", R.drawable.artist_photo_ruffalo),
                ActorForOldList("Chris Hemsworth", R.drawable.artist_photo_hemsworth)
            )
        )
    )
    list.add(
        MovieForOldList(
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
                ActorForOldList("Robert Downey Jr.", R.drawable.artist_photo_downey_jr),
                ActorForOldList("Chris Evans", R.drawable.artist_photo_evans),
                ActorForOldList("Mark Ruffalo", R.drawable.artist_photo_ruffalo),
                ActorForOldList("Chris Hemsworth", R.drawable.artist_photo_hemsworth),
                ActorForOldList("Robert Downey Jr.", R.drawable.artist_photo_downey_jr),
                ActorForOldList("Chris Evans", R.drawable.artist_photo_evans),
                ActorForOldList("Mark Ruffalo", R.drawable.artist_photo_ruffalo),
                ActorForOldList("Chris Hemsworth", R.drawable.artist_photo_hemsworth)
            )
        )
    )
    list.add(
        MovieForOldList(
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
                ActorForOldList("Robert Downey Jr.", R.drawable.artist_photo_downey_jr),
                ActorForOldList("Chris Evans", R.drawable.artist_photo_evans),
                ActorForOldList("Mark Ruffalo", R.drawable.artist_photo_ruffalo),
                ActorForOldList("Chris Hemsworth", R.drawable.artist_photo_hemsworth),
                ActorForOldList("Robert Downey Jr.", R.drawable.artist_photo_downey_jr),
                ActorForOldList("Chris Evans", R.drawable.artist_photo_evans),
                ActorForOldList("Mark Ruffalo", R.drawable.artist_photo_ruffalo),
                ActorForOldList("Chris Hemsworth", R.drawable.artist_photo_hemsworth)
            )
        )
    )

    return list
}