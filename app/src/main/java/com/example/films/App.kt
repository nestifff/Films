package com.example.films

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.work.*
import com.example.films.model.BackgroundUpdateFilms.NAME_UNIQUE_WORK
import com.example.films.model.BackgroundUpdateFilms.WorkerLoadFromAPI
import com.example.films.movieDetails.MovieDetailsViewModel
import com.example.films.moviesList.LoadMoviesProvider
import com.example.films.moviesList.MoviesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

class App : Application() {

    private val moviesListViewModelModule = module {

        single { LoadMoviesProvider() }

        viewModel { parameters ->
            MoviesListViewModel(
                applicationContext = parameters.get(),
                provider = get()
            )
        }
    }

    private val movieDetailsViewModelModule = module {
        viewModel { MovieDetailsViewModel() }
    }

    override fun onCreate() {

        super.onCreate()

        createWorkLoadMoviesInBackground()
        createNotificationsChannel()
        startKoinModules()
    }

    private fun startKoinModules() {
        startKoin {
            printLogger()
            modules(listOf(moviesListViewModelModule, movieDetailsViewModelModule))
        }
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

    private fun createWorkLoadMoviesInBackground() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workLoadMovies =
            PeriodicWorkRequestBuilder<WorkerLoadFromAPI>(
                8,
                TimeUnit.HOURS
            )
                .setInitialDelay(5, TimeUnit.SECONDS)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(applicationContext)
            .enqueueUniquePeriodicWork(
                NAME_UNIQUE_WORK,
                ExistingPeriodicWorkPolicy.REPLACE,
                workLoadMovies
            )
    }
}