package com.example.films.model.BackgroundUpdateFilms

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.films.TAG
import com.example.films.model.LoadAPIFunctionality.addMoviesToBD
import com.example.films.model.LoadAPIFunctionality.loadMoviesFromAPI
import com.example.films.model.dataClasses.Movie
import com.example.films.model.database.MoviesGenresDB
import com.example.films.moviesList.LoadMoviesProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.random.Random

class WorkerLoadFromAPI(
    private val context: Context,
    private val params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        val provider =
            LoadMoviesProvider()
        val coroutineScope = CoroutineScope(Dispatchers.IO)

        coroutineScope.launch {

            try {
                val database: MoviesGenresDB = MoviesGenresDB.create(context)
                val movies: MutableList<Movie> = loadMoviesFromAPI(provider)
                val genres = provider.genresList.genres

                addMoviesToBD(
                    genres = genres,
                    movies = movies,
                    database = database
                )

                val randomInd: Int = Random.nextInt(0, movies.size - 1)

                createNotificationRandomMovie(applicationContext, movies[randomInd])

            } catch (e: Exception) {
                Log.d(TAG, e.message ?: "")
            }
        }

        return Result.success()
    }

}

const val NAME_UNIQUE_WORK = "unique_work_load_movies_from_API"