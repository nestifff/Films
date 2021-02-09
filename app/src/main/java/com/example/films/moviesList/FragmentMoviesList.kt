package com.example.films.moviesList

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.films.App
import com.example.films.subjects.Movie
import com.example.films.R
import kotlinx.coroutines.*


class FragmentMoviesList : Fragment() {

    private var movies: List<Movie> = listOf()

    private val loadMoviesProvider: LoadMoviesProvider =
        LoadMoviesProvider(App.instance.applicationContext)

    private val viewModel by viewModels<MoviesListViewModel> {
        FragmentMoviesListViewModelFactory(
            loadMoviesProvider
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel.loadMovies()

        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.rv_films_list)

        val adapter = MoviesListAdapter(
            view.context,
            movies,
            activity as MoviesListClickListener
        )
        recyclerView.adapter = adapter

        recyclerView.layoutManager =
            when (resources.configuration.orientation) {

                ORIENTATION_PORTRAIT -> GridLayoutManager(view.context, 2)

                else -> LinearLayoutManager(
                    view.context,
                    LinearLayoutManager.HORIZONTAL, false
                )
            }

        listenMoviesData(recyclerView)

        return view
    }

    private fun listenMoviesData(recyclerView: RecyclerView) {
        viewModel.loadingMovies.observe(viewLifecycleOwner, Observer {
            movies = viewModel.loadingMovies.value ?: listOf()
            (recyclerView.adapter as MoviesListAdapter).movies = movies
            (recyclerView.adapter as MoviesListAdapter).notifyDataSetChanged()
        })
    }

}