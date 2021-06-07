package com.example.films.moviesList

import android.content.Context
import androidx.lifecycle.*
import com.example.films.model.LoadAPIFunctionality.loadGenresFromDB
import com.example.films.model.LoadAPIFunctionality.loadMoviesFromAPI
import com.example.films.model.LoadAPIFunctionality.loadMoviesFromDB
import com.example.films.model.LoadAPIFunctionality.updateDBMoviesAndGetNovelty
import com.example.films.model.dataClasses.Genre
import com.example.films.model.dataClasses.Movie
import com.example.films.model.database.MoviesGenresDB
import com.example.films.model.database.movies.MovieForDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesListViewModel(
    private val provider: LoadMoviesProvider?,
    private val applicationContext: Context
) : ViewModel() {

    private val _loadingMovies: MutableLiveData<MutableList<Movie>> = MutableLiveData(mutableListOf())
    val loadingMovies: LiveData<MutableList<Movie>>
        get() = _loadingMovies

    private var alreadyLoadedFromAPI = false

    private val database: MoviesGenresDB = MoviesGenresDB.create(applicationContext)
    private lateinit var genres: MutableList<Genre>
    private lateinit var moviesFromDB: MutableList<Movie>

    private lateinit var moviesFromAPI: MutableList<Movie>


    fun loadMovies() {

        viewModelScope.launch(Dispatchers.IO) {

            genres = loadGenresFromDB(database)
            moviesFromDB = loadMoviesFromDB(database, genres)

            if (moviesFromDB.size != 0) {
                _loadingMovies.postValue(moviesFromDB)

                if (!alreadyLoadedFromAPI) {
                    moviesFromAPI = loadMoviesFromAPI(provider)
                    alreadyLoadedFromAPI = true
                    // load from API
                    // update BD
                    updateDBMoviesAndGetNovelty(genres, moviesFromAPI, database)
                }

            } else {

                moviesFromAPI = loadMoviesFromAPI(provider)
                alreadyLoadedFromAPI = true
                _loadingMovies.postValue(moviesFromAPI)

                if (moviesFromAPI.isNotEmpty()) {
                    genres = provider?.genresList?.genres ?: genres
                    updateDBMoviesAndGetNovelty(genres, moviesFromAPI, database)
                }

            }


            /*genres = database.genreDao.getAllGenres()
            moviesForDB = database.movieDao.getAllMovies()

            if (!(genres.isEmpty() || moviesForDB.isEmpty())) {

                createMoviesListFromBDData()

                if (!alreadyLoadedFromAPI) {
                    moviesAddToBD = provider?.loadMoviesProvider() ?: mutableListOf()
                    moviesAddToBD.sortByDescending { it.rating }
                    alreadyLoadedFromAPI = true

                    if (moviesAddToBD.isNotEmpty()) {
                        genres = provider?.genresList?.genres ?: genres
                        addMoviesToBD()
                    }
                }

            } else {
                moviesAddToBD = provider?.loadMoviesProvider() ?: mutableListOf()
                moviesAddToBD.sortByDescending { it.rating }
                _loadingMovies.postValue(moviesAddToBD)
                alreadyLoadedFromAPI = true

                if (moviesAddToBD.isNotEmpty()) {
                    genres = provider?.genresList?.genres ?: genres
                    addMoviesToBD()
                }
            }*/
        }
    }

    /*private fun createMoviesListFromBDData() {

        val movies: MutableList<Movie> = mutableListOf()
        moviesForDB.sortBy { it.title }
        val genresMap: Map<Int, Genre> = genres.map {
            it.id to it
        }.toMap()

        var i = 0
        val currGenresId: MutableList<Int> = mutableListOf()

        while (i < moviesForDB.size) {
            if (i == 0 || moviesForDB[i].title != moviesForDB[i - 1].title) {
                val currGenres = mutableListOf<Genre>()
                for (id: Int in currGenresId) {
                    currGenres.add(genresMap[id] ?: Genre(0, ""))
                }
                movies.add(
                    Movie(
                        moviesForDB[i],
                        currGenres
                    )
                )
                currGenresId.clear()
                currGenresId.add(moviesForDB[i].genreID)
            } else {
                currGenresId.add(moviesForDB[i].genreID)
            }
            ++i
        }

        movies.sortByDescending { it.rating }
        _loadingMovies.postValue(movies)
    }*/

    private fun addMoviesToBD() {

        val genresMap: Map<Genre, Int> = genres.map {
            it to it.id
        }.toMap()
        val moviesRightType: MutableList<MovieForDB> = mutableListOf()
        var movieId = 1
        for (m: Movie in moviesFromAPI) {

            for (genre: Genre in m.genres) {
                moviesRightType.add(
                    MovieForDB(
                        id = movieId,
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
                ++movieId
            }
        }

        database.movieDao.deleteAllMovies()
        database.genreDao.deleteAllGenres()

        database.genreDao.insertGenres(genres = *genres.toTypedArray())
        database.movieDao.insertMovies(movies = *moviesRightType.toTypedArray())
    }

}

class FragmentMoviesListViewModelFactory(
    private val provider: LoadMoviesProvider?,
    private val applicationContext: Context
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MoviesListViewModel(provider, applicationContext) as T
    }
}