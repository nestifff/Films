package com.example.films.model.BackgroundUpdateFilms

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.films.*
import com.example.films.model.dataClasses.Movie
import java.io.IOException
import java.net.URL
import java.util.*


fun createNotificationRandomMovie(context: Context, movie: Movie) {

    var image: Bitmap? = null

    try {
        val url = URL(movie.backgroundImageUrl)
        image = BitmapFactory.decodeStream(url.openConnection().getInputStream())
    } catch (e: IOException) {
        Log.i(TAG, e.message ?: "")
    }

    val pendingIntent = PendingIntent.getActivity(
        context,
        REQUEST_CODE_START_FROM_NOTIFICATION,
        Intent(context, MainActivity::class.java)
            .setAction(Intent.ACTION_VIEW)
            .putExtra(PENDING_INTENT_GET_MOVIE_ID, movie.id),
        PendingIntent.FLAG_UPDATE_CURRENT
    )


    val notification = NotificationCompat.Builder(
        context,
        NOTIFICATION_CHANNEL_ID
    )
        .setContentTitle(movie.title)
        .setSmallIcon(R.drawable.ic_star_gray)
        .setContentText("ID: ${movie.id}, Rating: ${movie.rating}, Genres: ${movie.genres.map { it.name }}")
        .setStyle(NotificationCompat.BigPictureStyle().bigPicture(image))
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setWhen(Calendar.getInstance().timeInMillis)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .build()

    val notificationManager =
        NotificationManagerCompat.from(context)
    notificationManager.notify(TAG, NOTIFICATION_NEW_MOVIE_TAG, notification)
}

const val NOTIFICATION_NEW_MOVIE_TAG = 1313131313
const val PENDING_INTENT_GET_MOVIE_ID = "movieId"