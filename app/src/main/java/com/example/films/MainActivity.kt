package com.example.films

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.example.films.model.BackgroundUpdateFilms.PENDING_INTENT_GET_MOVIE_ID
import com.example.films.model.LoadAPIFunctionality.getDataFromAPIClasses.SearchRequestAPICreator
import com.example.films.model.dataClasses.Movie
import com.example.films.movieDetails.FragmentMoviesDetails
import com.example.films.movieDetails.MOVIE_ID_IN_BUNDLE_FRAGMENT
import com.example.films.movieDetails.MOVIE_IN_BUNDLE_FRAGMENT
import com.example.films.moviesList.FragmentMoviesList
import com.example.films.moviesList.MOVIES_KEY
import com.example.films.moviesList.MoviesListClickListener
import com.example.films.moviesList.SEARCH_LINE_KEY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(),
    FragmentMoviesDetails.TransactionsFragmentMDClicks,
    MoviesListClickListener,
    FragmentMoviesList.SearchMoviesOnClick {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.supportActionBar?.hide()
        Log.i(TAG, "In MainActivity")

        if (intent.action == Intent.ACTION_VIEW) {
            showMovieDetails(intent)
        }
    }

    override fun onNewIntent(intent: Intent?) {

        super.onNewIntent(intent)

        if (intent?.action == Intent.ACTION_VIEW) {
            showMovieDetails(intent)
        }
    }

    private fun showMovieDetails(intent: Intent) {

        while (supportFragmentManager.backStackEntryCount != 0) {
            supportFragmentManager.popBackStack()
        }

        val id = intent.getIntExtra(PENDING_INTENT_GET_MOVIE_ID, -1)

        val fragmentMoviesDetails = FragmentMoviesDetails()
        val bundle = bundleOf(MOVIE_ID_IN_BUNDLE_FRAGMENT to id)
        fragmentMoviesDetails.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragmentMoviesDetails)
            .addToBackStack(null)
            .commit()
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

    override fun searchMoviesOnClick(searchLine: String) {

        val coroutineScope = CoroutineScope(Dispatchers.IO)

        coroutineScope.launch {

            val movies =
                SearchRequestAPICreator()
                    .getMovies(searchLine)
            movies.sortByDescending { it.reviewCount }


            withContext(Dispatchers.Main) {

                val fragment = FragmentMoviesList()

                val bundle = bundleOf(
                    MOVIES_KEY to movies,
                    SEARCH_LINE_KEY to searchLine
                )
                fragment.arguments = bundle

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }

        }
    }
}