package com.example.films.moviesList

import com.example.films.model.LoadAPIFunctionality.GenresList
import com.example.films.model.LoadAPIFunctionality.getDataFromAPIClasses.GenresListAPICreator
import com.example.films.model.LoadAPIFunctionality.getDataFromAPIClasses.MoviesListAPICreator
import com.example.films.model.dataClasses.Movie

class LoadMoviesProvider :
    LoadMovies {

    val genresList = GenresList()
    private val genresListCreator =
        GenresListAPICreator()
    private val moviesListCreator =
        MoviesListAPICreator(
            genresList
        )

    override suspend fun loadMoviesProvider(): MutableList<Movie> {

        genresList.addGenres(genresListCreator.loadGenres())

        return moviesListCreator.loadMovies()
    }

}

interface LoadMovies {
    suspend fun loadMoviesProvider(): List<Movie>
}