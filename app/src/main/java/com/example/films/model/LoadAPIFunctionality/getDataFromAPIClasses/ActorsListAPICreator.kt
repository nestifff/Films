package com.example.films.model.LoadAPIFunctionality.getDataFromAPIClasses

import android.util.Log
import com.android.academy.fundamentals.homework.data.JsonGenre
import com.example.films.*
import com.example.films.model.dataClasses.Actor
import com.example.films.model.dataClasses.Genre
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class ActorsListAPICreator {

    private val jsonFormat = Json { ignoreUnknownKeys = true }

    suspend fun loadActors(id: Int): List<Actor> = withContext(Dispatchers.IO) {

        var actors: List<Actor> = listOf()
        try {
            createGetActorsCall(id).execute().use { response ->
                actors = successfulLoadActors(response, id)
            }
        } catch (e: Exception) {
            errorLoadingActors(e)
        }

        actors
    }

    private fun createGetActorsCall(id: Int) = OkHttpClient().newCall(createGetActorsRequest(id))

    private fun createGetActorsRequest(id: Int) = Request.Builder()
        .get()
        .url("$APIUrl/3/movie/$id/credits?api_key=$APIKey&language=en-US")
        .build()

    private fun errorLoadingActors(error: Throwable) {
        Log.e(TAG, error.stackTraceToString())
    }

    private suspend fun successfulLoadActors(response: Response, id: Int): List<Actor> {

        var actorsJson = response.body?.string() ?: ""

        if (actorsJson == "") {
            return mutableListOf()
        }

        actorsJson = actorsJson.substringAfter("{\"id\":$id,\"cast\":").substringBeforeLast(
            ",\"crew\":["
        )

        return loadActorsJson(actorsJson)
    }

    private suspend fun loadActorsJson(data: String): List<Actor> = withContext(Dispatchers.IO) {

        val actors = jsonFormat.decodeFromString<List<Actor>>(data)

        for (actor in actors) {
            actor.imageUrl =
                if (actor.imageUrl == null) {
                    null
                } else {
                    "$APIBaseUrl$widthActorImage/${actor.imageUrl}"
                }

        }
        actors
    }
}