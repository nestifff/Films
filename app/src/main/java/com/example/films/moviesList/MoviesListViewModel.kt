package com.example.films.moviesList

import androidx.lifecycle.*
import com.example.films.subjects.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesListViewModel(private val provider: LoadMoviesProvider?) : ViewModel() {

    private val _loadingMovies: MutableLiveData<List<Movie>> = MutableLiveData(emptyList())
    val loadingMovies: LiveData<List<Movie>>
        get() = _loadingMovies

    fun loadMovies() {
        viewModelScope.launch (Dispatchers.IO) {
            val movies: List<Movie> = provider?.loadMoviesProvider() ?: listOf()
            _loadingMovies.postValue(movies)
        }
    }

}

class FragmentMoviesListViewModelFactory(private val provider: LoadMoviesProvider?):
                                                        ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MoviesListViewModel(provider) as T
    }
}