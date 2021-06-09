package com.example.films.model.BackgroundUpdateFilms

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.films.TAG
import com.example.films.model.LoadAPIFunctionality.loadMoviesFromAPI
import com.example.films.model.LoadAPIFunctionality.updateDBMoviesAndGetNovelty
import com.example.films.model.dataClasses.Movie
import com.example.films.model.database.MoviesGenresDB
import com.example.films.moviesList.LoadMoviesProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class WorkerLoadFromAPI(
    private val context: Context,
    private val params: WorkerParameters
): Worker(context, params) {

    override fun doWork(): Result {
        val provider =
            LoadMoviesProvider()
        val coroutineScope = CoroutineScope(Dispatchers.IO)

        coroutineScope.launch {

            try {
                val database: MoviesGenresDB = MoviesGenresDB.create(context)
                val movies: MutableList<Movie> = loadMoviesFromAPI(provider)
                val genres = provider.genresList.genres

                val moviesNovelty =
                    updateDBMoviesAndGetNovelty(genres, movies, database)

                if (moviesNovelty.size != 0) {
                    moviesNovelty.sortBy { it.rating }
//                    createNotificationNewMovie(applicationContext, moviesNovelty[0])
                }

            } catch (e: Exception) {
                Log.d(TAG, e.message ?: "")
            }
        }

        return Result.success()
    }

}

const val NAME_UNIQUE_WORK = "unique_work_load_movies_from_API"