package com.example.films.model.LoadAPIFunctionality.getDataFromAPIClasses

import android.util.Log
import com.android.academy.fundamentals.homework.data.JsonMovie
import com.example.films.*
import com.example.films.model.LoadAPIFunctionality.jsonClasses.JsonMovieDetails
import com.example.films.model.dataClasses.Actor
import com.example.films.model.dataClasses.Genre
import com.example.films.model.dataClasses.Movie
import com.example.films.model.dataClasses.MovieDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class MovieDetailsAPICreator {

    private val jsonFormat = Json { ignoreUnknownKeys = true }
    private var movieDetails: MovieDetails? = null
    private var actors = listOf<Actor>()

    suspend fun loadMovieDetails(id: Int): MovieDetails? = withContext(Dispatchers.IO) {

        actors = ActorsListAPICreator().loadActors(id)

        try {
            createGetMovieDetailsCall(id).execute().use { response ->
                successfulLoadMovies(response)
            }
        } catch (e: Exception) {
            errorLoadingMovies(e)
        }

        movieDetails
    }

    private fun createGetMovieDetailsCall(id: Int) =
        OkHttpClient().newCall(createGetMovieDetailsRequest(id))

    private fun createGetMovieDetailsRequest(id: Int) = Request.Builder()
        .get()
        .url("$APIUrl/3/movie/$id?api_key=$APIKey&language=en-US")
        .build()

    private fun errorLoadingMovies(error: Throwable) {
        Log.e(TAG, error.stackTraceToString())
    }

    private suspend fun successfulLoadMovies(response: Response) {

        var moviesJson = response.body?.string() ?: ""

        if (moviesJson == "") {
            return
        }
        Log.i(TAG, moviesJson.length.toString())
        movieDetails = loadMovieDetailsJson(moviesJson)
    }

    private suspend fun loadMovieDetailsJson(dataStr: String): MovieDetails =
        withContext(Dispatchers.IO) {

            val jsonMovieDetails = jsonFormat.decodeFromString<JsonMovieDetails>(dataStr)

            MovieDetails(
                jsonMovieDetails.id,
                age = if (jsonMovieDetails.adult != false) 16 else 13,
                jsonMovieDetails.title,
                genres = jsonMovieDetails.genres,
                jsonMovieDetails.votesCount ?: 0,
                false,
                jsonMovieDetails.ratings ?: 0f,
                jsonMovieDetails.posterPicture ?: "",
                "${APIBaseUrl}${widthBackgroundImage}/${jsonMovieDetails.backdropPicture ?: "/xRI636TOdS1K1GBqIBRSmfZ1T5x.jpg"}",
                jsonMovieDetails.overview ?: "",
                jsonMovieDetails.budget,
                jsonMovieDetails.revenue,
                jsonMovieDetails.tagline,
                jsonMovieDetails.runtime,
                jsonMovieDetails.releaseDate,
                jsonMovieDetails.homepage,
                actors
            )
        }

}