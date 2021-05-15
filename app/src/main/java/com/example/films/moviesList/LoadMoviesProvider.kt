package com.example.films.moviesList

import com.example.films.model.GenresList
import com.example.films.model.LoadAPIFunctionality.GenresListAPICreator
import com.example.films.model.dataClasses.Movie
import com.example.films.model.LoadAPIFunctionality.MoviesListAPICreator

class LoadMoviesProvider :
    LoadMovies {

    val genresList = GenresList()
    private val genresListCreator =
        GenresListAPICreator()
    private val moviesListCreator =
        MoviesListAPICreator(genresList)

    override suspend fun loadMoviesProvider(): MutableList<Movie> {

        genresList.addGenres(genresListCreator.loadGenres())

        return moviesListCreator.loadMovies()
    }

}

interface LoadMovies {
    suspend fun loadMoviesProvider(): List<Movie>
}