package com.example.films.model.LoadAPIFunctionality

import com.example.films.model.dataClasses.Genre
import com.example.films.model.dataClasses.Movie
import com.example.films.model.database.movies.MovieForDB

fun joinMoviesWithGenes(moviesOld: MutableList<MovieForDB>, genres: List<Genre>): MutableList<Movie> {

    val movies: MutableList<Movie> = mutableListOf()
    moviesOld.sortBy { it.title }
    val genresMap: Map<Int, Genre> = genres.map {
        it.id to it
    }.toMap()

    var i = 0
    val currGenresId: MutableList<Int> = mutableListOf()

    while (i < moviesOld.size) {
        if (i == 0 || moviesOld[i].title != moviesOld[i - 1].title) {
            val currGenres = mutableListOf<Genre>()
            for (id: Int in currGenresId) {
                currGenres.add(genresMap[id] ?: Genre(0, ""))
            }
            movies.add(
                Movie(
                    moviesOld[i],
                    currGenres
                )
            )
            currGenresId.clear()
            currGenresId.add(moviesOld[i].genreID)
        } else {
            currGenresId.add(moviesOld[i].genreID)
        }
        ++i
    }

    movies.sortByDescending { it.rating }
    return movies
}