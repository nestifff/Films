package com.example.films

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.example.films.movieDetails.FragmentMoviesDetails
import com.example.films.movieDetails.MOVIE_IN_BUNDLE_FRAGMENT
import com.example.films.moviesList.FragmentMoviesList
import com.example.films.moviesList.MOVIES_KEY
import com.example.films.moviesList.MoviesListClickListener
import com.example.films.moviesList.SEARCH_LINE_KEY
import com.example.films.model.dataClasses.Movie

class MainActivity : AppCompatActivity(),
            FragmentMoviesDetails.TransactionsFragmentMDClicks,
    MoviesListClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.supportActionBar?.hide()
    }


    override fun backOnClick() {
        supportFragmentManager.popBackStack()
    }

    override fun movieOnClick(movie: Movie) {

        val fragment = FragmentMoviesDetails()
        val bundle = bundleOf(MOVIE_IN_BUNDLE_FRAGMENT to movie)
        fragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun changeMoviesList(newMovies: List<Movie>, searchLine: String) {

        val fragment = FragmentMoviesList()

        val bundle = bundleOf(
            MOVIES_KEY to newMovies,
            SEARCH_LINE_KEY to searchLine
        )
        fragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}