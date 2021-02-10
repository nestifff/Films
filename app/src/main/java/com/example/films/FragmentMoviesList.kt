package com.example.films

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.films.data.JsonMovieRepository
import kotlinx.coroutines.*


class FragmentMoviesList : Fragment() {

    private val scope = CoroutineScope(Dispatchers.IO)
    private var movies: List<Movie> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.rv_films_list)

        val adapter = MoviesListAdapter(view.context, movies, activity as MoviesListClickListener)
        recyclerView.adapter = adapter

        recyclerView.layoutManager =
            when (resources.configuration.orientation) {

                ORIENTATION_PORTRAIT -> GridLayoutManager(view.context, 2)

                else -> LinearLayoutManager(
                    view.context,
                    LinearLayoutManager.HORIZONTAL, false
                )


            }

        scope.launch {
            val result = async { loadMoviesThisFragment(view) }
            result.await()
            withContext(Dispatchers.Main) {
                (recyclerView.adapter as MoviesListAdapter).movies = movies
                (recyclerView.adapter as MoviesListAdapter).notifyDataSetChanged()
            }
        }

        return view
    }

    private suspend fun loadMoviesThisFragment(view:View){
        movies = JsonMovieRepository(view.context).loadMovies()
    }

}