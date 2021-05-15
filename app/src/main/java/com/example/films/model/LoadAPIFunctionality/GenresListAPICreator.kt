package com.example.films.model.LoadAPIFunctionality

import android.util.Log
import com.android.academy.fundamentals.homework.data.JsonGenre
import com.example.films.TAG
import com.example.films.model.dataClasses.Genre
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.*

class GenresListAPICreator() {

    private val jsonFormat = Json { ignoreUnknownKeys = true }
    private var genres = listOf<Genre>()

    suspend fun loadGenres(): List<Genre> = withContext(Dispatchers.IO) {

        if (genres.isNotEmpty()) {
            genres

        } else {

            try {
                createGetGenresCall().execute().use { response ->
                    genres = successfulLoadGenres(response)
                }
            } catch (e: Exception) {
                errorLoadingGenres(e)
            }

            for (genre in genres) {
                Log.i(TAG, genre.name)
            }
            genres
        }
    }

    private fun createGetGenresCall() = OkHttpClient().newCall(createGetGenresRequest())

    private fun createGetGenresRequest() = Request.Builder()
        .get()
        .url("https://api.themoviedb.org/3/genre/movie/list?api_key=24ab06e71fc730e392e92a68c467de4c&language=en-US")
        .build()

    private fun errorLoadingGenres(error: Throwable) {
        Log.e(TAG, error.stackTraceToString())
    }

    private suspend fun successfulLoadGenres(response: Response): List<Genre> {

        var genresJson = response.body?.string() ?: ""

        if (genresJson == "") {
            return listOf()
        }

        genresJson = genresJson.substringAfter("[").substringBefore(']')
        genresJson = "[$genresJson]"
        Log.i(TAG, genresJson)

        return loadGenresJson(genresJson)
    }

    private suspend fun loadGenresJson(data: String): List<Genre> = withContext(Dispatchers.IO) {
        val jsonGenres = jsonFormat.decodeFromString<List<JsonGenre>>(data)
        jsonGenres.map { jsonGenre ->
            Genre(
                id = jsonGenre.id,
                name = jsonGenre.name
            )
        }
    }


}