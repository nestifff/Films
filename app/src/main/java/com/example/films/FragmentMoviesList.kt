package com.example.films

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class FragmentMoviesList : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.rv_films_list)
        val movies: List<Movie> = generateMovies(view.context)
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

        return view
    }

}