package com.example.films.moviesList

import android.content.Context
import androidx.lifecycle.*
import com.example.films.model.LoadAPIFunctionality.addMoviesToBD
import com.example.films.model.LoadAPIFunctionality.loadGenresFromDB
import com.example.films.model.LoadAPIFunctionality.loadMoviesFromAPI
import com.example.films.model.LoadAPIFunctionality.loadMoviesFromDB
import com.example.films.model.dataClasses.Genre
import com.example.films.model.dataClasses.Movie
import com.example.films.model.database.MoviesGenresDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MoviesListViewModel(
    private val provider: LoadMoviesProvider?,
    private val applicationContext: Context
) : ViewModel() {

    private val _loadingMovies: MutableLiveData<MutableList<Movie>> =
        MutableLiveData(mutableListOf())
    val loadingMovies: LiveData<MutableList<Movie>>
        get() = _loadingMovies

    private var isLoadedFromAPI = false

    private val database: MoviesGenresDB = MoviesGenresDB.create(applicationContext)
    private lateinit var genres: MutableList<Genre>
    private lateinit var moviesFromDB: MutableList<Movie>

    private lateinit var moviesFromAPI: MutableList<Movie>


    fun loadMovies() {

        viewModelScope.launch(Dispatchers.IO) {

            genres = loadGenresFromDB(database)
            moviesFromDB = loadMoviesFromDB(database, genres)

            if (moviesFromDB.isEmpty()) {

                moviesFromAPI = loadMoviesFromAPI(provider)

                if (moviesFromAPI.isNotEmpty()) {

                    _loadingMovies.postValue(moviesFromAPI)

                    isLoadedFromAPI = true
                    genres = provider?.genresList?.genres ?: genres

                    addMoviesToBD(
                        genres = genres,
                        movies = moviesFromAPI,
                        database = database
                    )
                }

            } else {

                _loadingMovies.postValue(moviesFromDB)

                if (!isLoadedFromAPI) {

                    moviesFromAPI = loadMoviesFromAPI(provider)

                    if (moviesFromAPI.isNotEmpty()) {

                        genres = provider?.genresList?.genres ?: genres
                        isLoadedFromAPI = true

                        addMoviesToBD(
                            genres = genres,
                            movies = moviesFromAPI,
                            database = database
                        )

                    }
                }
            }
        }

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