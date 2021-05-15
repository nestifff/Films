package com.example.films.model.LoadAPIFunctionality

import android.util.Log
import com.android.academy.fundamentals.homework.data.JsonMovie
import com.example.films.APIBaseUrl
import com.example.films.TAG
import com.example.films.model.GenresList
import com.example.films.model.dataClasses.Genre
import com.example.films.model.dataClasses.Movie
import com.example.films.widthBackgroundImage
import com.example.films.widthPosterImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class MoviesListAPICreator(
    private val genresList: GenresList
) {

    private val jsonFormat = Json { ignoreUnknownKeys = true }
    private var movies = mutableListOf<Movie>()

    suspend fun loadMovies(): MutableList<Movie> = withContext(Dispatchers.IO) {

        if (movies.isNotEmpty()) {
            movies

        } else {

            for (i in 1..401 step 80)
            try {
                createGetMoviesCall(i).execute().use { response ->
                    successfulLoadMovies(response)
                }
            } catch (e: Exception) {
                errorLoadingMovies(e)
            }

            movies
        }
    }

    private fun createGetMoviesCall(i: Int) = OkHttpClient().newCall(createGetMoviesRequest(i))

    private fun createGetMoviesRequest(i: Int) = Request.Builder()
        .get()
        .url("https://api.themoviedb.org/3/movie/top_rated?api_key=24ab06e71fc730e392e92a68c467de4c&language=en-US&page=$i")
        .build()

    private fun errorLoadingMovies(error: Throwable) {
        Log.e(TAG, error.stackTraceToString())
    }

    private suspend fun successfulLoadMovies(response: Response) {

        var moviesJson = response.body?.string() ?: ""

        if (moviesJson == "") {
            return
        }

        moviesJson = moviesJson.substringAfter("[").substringBefore("],\"total_pages\"")
        moviesJson = "[$moviesJson]"
        Log.i(TAG, moviesJson.length.toString())

        movies.addAll(loadMoviesJson(moviesJson))
    }

    private suspend fun loadMoviesJson(dataStr: String): List<Movie> = withContext(Dispatchers.IO) {

        val jsonMovies = jsonFormat.decodeFromString<List<JsonMovie>>(dataStr)

        jsonMovies.map {

            Movie(
                id = it.id,
                title = it.title,
                age = if (it.adult == null || it.adult) 18 else 12,
                genres = getGenres(it.genreIds ?: listOf()),
                reviewCount = it.votesCount ?: 0,
                isLiked = false,
                rating = it.ratings ?: 0f,
                posterImageUrl = "${APIBaseUrl}${widthPosterImage}/${it.posterPicture ?: "/2CAL2433ZeIihfX1Hb2139CX0pW.jpg"}",
                backgroundImageUrl = "${APIBaseUrl}${widthBackgroundImage}/${it.backdropPicture ?: "/xRI636TOdS1K1GBqIBRSmfZ1T5x.jpg"}",
                storyLine = it.overview ?: "No overview",
            )
        }
    }

    private fun getGenres(ids: List<Int>): List<Genre> {

        val currGenres: MutableList<Genre> = mutableListOf()

        for (id in ids) {
            currGenres.add(genresList.getGenre(id))
        }

        return currGenres

    }

}