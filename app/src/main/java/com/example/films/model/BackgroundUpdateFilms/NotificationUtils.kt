package com.example.films.model.BackgroundUpdateFilms

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.films.NOTIFICATION_CHANNEL_ID
import com.example.films.R
import com.example.films.TAG
import com.example.films.model.dataClasses.Movie

fun createNotificationNewMovie(context: Context, movie: Movie) {

    val notification = NotificationCompat.Builder(
        context,
        NOTIFICATION_CHANNEL_ID
    )
        .setContentTitle("You have new movie in your app!")
        .setSmallIcon(R.drawable.ic_star_gray)
        .setContentText(movie.title)
        .setStyle(NotificationCompat.BigTextStyle().bigText(movie.storyLine))
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .build()

    val notificationManager =
        NotificationManagerCompat.from(context)
    notificationManager.notify(TAG, NOTIFICATION_NEW_MOVIE_TAG, notification)
}

const val NOTIFICATION_NEW_MOVIE_TAG = 1313131313