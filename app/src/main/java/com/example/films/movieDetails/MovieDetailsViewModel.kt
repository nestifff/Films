package com.example.films.movieDetails

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.films.model.LoadAPIFunctionality.getDataFromAPIClasses.MovieDetailsAPICreator
import com.example.films.model.dataClasses.Movie
import com.example.films.model.dataClasses.MovieDetails
import kotlinx.coroutines.launch


class MovieDetailsViewModel(): ViewModel() {

    private val _loadingMovie: MutableLiveData<MovieDetails> = MutableLiveData()
    val loadingMovie: LiveData<MovieDetails>
        get() = _loadingMovie

    fun loadMovie(movie: Movie) {

        viewModelScope.launch {
            val movieDetails: MovieDetails =
                MovieDetailsAPICreator().loadMovieDetails(movie)

            _loadingMovie.postValue(movieDetails)
        }
    }

}