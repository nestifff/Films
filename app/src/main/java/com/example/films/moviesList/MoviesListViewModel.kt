package com.example.films.moviesList

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.films.TAG
import com.example.films.model.LoadAPIFunctionality.*
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
                    val str = moviesFromAPI.find { it.title == "Joker" }?.id
                    Log.i(TAG, "From loadMovies ViewModel moviesFromAPI: $str")
                    isLoadedFromAPI = true
                    genres = provider?.genresList?.genres ?: genres
                    updateDBMoviesAndGetNovelty(genres, moviesFromAPI, database)
                }

            } else {

                _loadingMovies.postValue(moviesFromDB)
                val str = moviesFromDB.find { it.title == "Joker" }?.id
                Log.i(TAG, "From loadMovies ViewModel moviesFromDB: $str")

                if (!isLoadedFromAPI) {

                    moviesFromAPI = loadMoviesFromAPI(provider)
                    val str = moviesFromAPI.find { it.title == "Joker" }?.id
                    Log.i(TAG, "From loadMovies ViewModel moviesFromAPI: $str")

                    if (moviesFromAPI.isNotEmpty()) {

                        genres = provider?.genresList?.genres ?: genres
                        isLoadedFromAPI = true

                        val moviesNovelty =
                            updateDBMoviesAndGetNovelty(genres, moviesFromAPI, database)
                        if (moviesNovelty.isNotEmpty()) {
                            _loadingMovies.postValue(moviesFromAPI)
                        }
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