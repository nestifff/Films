package com.example.films.moviesList

import android.content.Context
import com.example.films.subjects.Movie
import com.example.films.data.JsonMovieRepository

class LoadMoviesProvider(val context: Context) :
    LoadMovies {

    override suspend fun loadMoviesProvider(): List<Movie> {
        return JsonMovieRepository(context).loadMovies()
    }

}

interface LoadMovies {
    suspend fun loadMoviesProvider (): List<Movie>
}