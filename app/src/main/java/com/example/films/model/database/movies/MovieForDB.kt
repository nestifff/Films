package com.example.films.model.database.movies

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.example.films.model.dataClasses.Genre
import java.io.Serializable

@Entity(tableName = "movies")
data class MovieForDB(

    @ColumnInfo(name = "id_api")
    val idAPI: Int,

    @ColumnInfo(name = "age")
    val age: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "genre_id")
    val genreID: Int,

    @ColumnInfo(name = "review_count")
    val reviewCount: Int,

    @ColumnInfo(name = "is_liked")
    var isLiked: Boolean,

    @ColumnInfo(name = "rating")
    val rating: Float,

    @ColumnInfo(name = "poster_image_url")
    val posterImageUrl: String,

    @ColumnInfo(name = "background_image_url")
    val backgroundImageUrl: String,

    @ColumnInfo(name = "story_line")
    val storyLine: String,
) : Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}