package com.example.films.model.LoadAPIFunctionality

import com.example.films.model.dataClasses.Movie
import com.example.films.model.database.MoviesGenresDB
import com.example.films.moviesList.LoadMoviesProvider


fun loadMoviesFromDB(database: MoviesGenresDB): MutableList<Movie> {

    val genres = database.genreDao.getAllGenres()
    val moviesForDB = database.movieDao.getAllMovies()

    return if (!(genres.isEmpty() || moviesForDB.isEmpty())) {
        joinMoviesWithGenes(moviesForDB, genres)

    } else {
        mutableListOf()
    }
}

suspend fun loadMoviesFromAPI(provider: LoadMoviesProvider?): MutableList<Movie> {

    val movies = provider?.loadMoviesProvider() ?: mutableListOf()
    movies.sortByDescending { it.rating }
    return movies
}