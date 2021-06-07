package com.example.films.model.database.movies

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieDao {

    @Query("SELECT * from movies")
    fun getAllMovies(): MutableList<MovieForDB>

    @Query("DELETE from movies")
    fun deleteAllMovies()

    @Insert
    fun insertMovies(vararg movies: MovieForDB)

    @Query("SELECT * FROM movies WHERE id=:id ")
    fun findMovieByID(id: Int): MovieForDB?

}