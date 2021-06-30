package com.example.films

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.work.*
import com.example.films.model.LoadAPIFunctionality.getDataFromAPIClasses.SearchRequestAPICreator
import com.example.films.model.BackgroundUpdateFilms.NAME_UNIQUE_WORK
import com.example.films.model.BackgroundUpdateFilms.WorkerLoadFromAPI
import com.example.films.model.dataClasses.Movie
import com.example.films.movieDetails.FragmentMoviesDetails
import com.example.films.movieDetails.MOVIE_IN_BUNDLE_FRAGMENT
import com.example.films.moviesList.FragmentMoviesList
import com.example.films.moviesList.MOVIES_KEY
import com.example.films.moviesList.MoviesListClickListener
import com.example.films.moviesList.SEARCH_LINE_KEY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(),
    FragmentMoviesDetails.TransactionsFragmentMDClicks,
    MoviesListClickListener,
    FragmentMoviesList.SearchMoviesOnClick {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.supportActionBar?.hide()
        Log.i(TAG, "In MainActivity")

//        createWorkLoadMoviesInBackground()
        createNotificationsChannel()
    }


    override fun backOnClick() {
        supportFragmentManager.popBackStack()
    }

    override fun movieOnClick(movie: Movie) {

        val fragment = FragmentMoviesDetails()
        val bundle = bundleOf(MOVIE_IN_BUNDLE_FRAGMENT to movie)
        fragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }


    private fun createWorkLoadMoviesInBackground() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        val workLoadMovies =
            PeriodicWorkRequestBuilder<WorkerLoadFromAPI>(
                8,
                TimeUnit.HOURS
            )
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(applicationContext)
            .enqueueUniquePeriodicWork(
                NAME_UNIQUE_WORK,
                ExistingPeriodicWorkPolicy.REPLACE,
                workLoadMovies
            )
    }

    private fun createNotificationsChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Notifications about new movies",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }

    }

    override fun searchMoviesOnClick(searchLine: String) {

        val coroutineScope = CoroutineScope(Dispatchers.IO)

        coroutineScope.launch {

            val movies =
                SearchRequestAPICreator()
                    .getMovies(searchLine)
            movies.sortByDescending { it.reviewCount }


            withContext(Dispatchers.Main) {

                val fragment = FragmentMoviesList()

                val bundle = bundleOf(
                    MOVIES_KEY to movies,
                    SEARCH_LINE_KEY to searchLine
                )
                fragment.arguments = bundle

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }

        }
    }
}