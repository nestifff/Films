package com.example.films

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.work.*
import com.example.films.model.BackgroundUpdateFilms.NAME_UNIQUE_WORK
import com.example.films.model.BackgroundUpdateFilms.WorkerLoadFromAPI
import java.util.concurrent.TimeUnit

class App: Application() {

    override fun onCreate() {

        super.onCreate()

        createWorkLoadMoviesInBackground()
        createNotificationsChannel()
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