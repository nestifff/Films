package com.example.films.model.database.genres

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.films.model.dataClasses.Genre

@Dao
interface GenreDao {

    @Query("SELECT * from genres")
    fun getAllGenres(): MutableList<Genre>

    @Query("DELETE from genres")
    fun deleteAllGenres()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenres(vararg genres: Genre)
}