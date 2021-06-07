package com.example.films.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.films.model.dataClasses.Genre
import com.example.films.model.database.genres.GenreDao
import com.example.films.model.database.movies.MovieDao
import com.example.films.model.database.movies.MovieForDB

@Database(
    entities = [MovieForDB::class, Genre::class],
    version = 2
)
abstract class MoviesGenresDB : RoomDatabase() {

    abstract val movieDao: MovieDao
    abstract val genreDao: GenreDao

    companion object {
        private const val DATABASE_NAME = "Movies.db"

        fun create(applicationContext: Context): MoviesGenresDB = Room.databaseBuilder(
            applicationContext,
            MoviesGenresDB::class.java,
            DATABASE_NAME
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
}