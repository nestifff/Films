package com.example.films.model

import com.example.films.model.dataClasses.Genre

class GenresList {

    val genres: MutableList<Genre> = mutableListOf()

    fun addGenres(newGenres: List<Genre>) {

        for (newGenre in newGenres) {
            genres.add(newGenre)
        }
        genres.sortBy { it.id }
    }

    fun getGenre(id: Int): Genre {

        return genres.firstOrNull{
            it.id == id
        } ?: Genre(1, "Default Genre")
    }
}