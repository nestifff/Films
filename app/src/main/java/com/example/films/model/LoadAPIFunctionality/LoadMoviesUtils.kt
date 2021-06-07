package com.example.films.model.LoadAPIFunctionality

import com.example.films.model.dataClasses.Genre
import com.example.films.model.dataClasses.Movie
import com.example.films.model.database.MoviesGenresDB
import com.example.films.model.database.movies.MovieForDB
import com.example.films.moviesList.LoadMoviesProvider


fun loadGenresFromDB(database: MoviesGenresDB): MutableList<Genre> {
    return database.genreDao.getAllGenres()
}

fun loadMoviesFromDB(
    database: MoviesGenresDB,
    genres: MutableList<Genre>
): MutableList<Movie> {

    val moviesForDB = database.movieDao.getAllMovies()

    return if (!(genres.isEmpty() || moviesForDB.isEmpty())) {
        joinMoviesWithGenes(moviesForDB, genres)

    } else {
        mutableListOf()
    }
}

suspend fun loadMoviesFromAPI(provider: LoadMoviesProvider?): MutableList<Movie> {

    val movies = provider?.loadMoviesProvider() ?: mutableListOf()
    movies.sortByDescending { it.rating }
    return movies
}

fun updateDBMoviesAndGetNovelty(

    genres: MutableList<Genre>,
    newMovies: MutableList<Movie>,
    database: MoviesGenresDB

): MutableList<Movie> {

    val movieNovelties: MutableList<Movie> = mutableListOf()

    var movie: MovieForDB?

    for (newMovie in newMovies) {
        movie = database.movieDao.findMovieByID(newMovie.id)
        if (movie == null) {
            movieNovelties.add(newMovie)
        }
    }

    addMoviesToBD(newMovies, genres, database)

    return movieNovelties

}

fun addMoviesToBD(
    movies: MutableList<Movie>,
    genres: MutableList<Genre>,
    database: MoviesGenresDB
) {

    if (movies.isEmpty()) {
        return
    }

    val genresMap: Map<Genre, Int> = genres.map {
        it to it.id
    }.toMap()

    val moviesRightType: MutableList<MovieForDB> = mutableListOf()
    for (m: Movie in movies) {

        for (genre: Genre in m.genres) {
            moviesRightType.add(
                MovieForDB(
                    idAPI = m.id,
                    age = m.age,
                    title = m.title,
                    genreID = genresMap[genre] ?: 0,
                    reviewCount = m.reviewCount,
                    isLiked = m.isLiked,
                    rating = m.rating,
                    posterImageUrl = m.posterImageUrl,
                    backgroundImageUrl = m.backgroundImageUrl,
                    storyLine = m.storyLine
                )
            )
        }
    }

    database.movieDao.deleteAllMovies()
    database.genreDao.deleteAllGenres()

    database.genreDao.insertGenres(genres = *genres.toTypedArray())
    database.movieDao.insertMovies(movies = *moviesRightType.toTypedArray())
}