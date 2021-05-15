package com.example.films.moviesList

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.films.MainActivity
import com.example.films.model.dataClasses.Movie
import com.example.films.R
import com.example.films.model.LoadAPIFunctionality.SearchRequestAPICreator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FragmentMoviesList : Fragment() {

    private var movies: MutableList<Movie> = mutableListOf()

    private val loadMoviesProvider: LoadMoviesProvider = LoadMoviesProvider()
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    private var searchLine = ""

    private val viewModel by viewModels<MoviesListViewModel> {
        FragmentMoviesListViewModelFactory(
            loadMoviesProvider,
            requireContext().applicationContext
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.rv_films_list)
        val searchIcon = view.findViewById<ImageView>(R.id.iv_search)
        val searchEditText = view.findViewById<EditText>(R.id.et_search)

        var searchRequest: String

        if (arguments != null) {
            movies = arguments?.getSerializable(MOVIES_KEY) as MutableList<Movie>? ?: mutableListOf()
            searchLine = arguments?.getString(SEARCH_LINE_KEY) ?: ""
            searchEditText.visibility = View.VISIBLE

        } else {
            viewModel.loadMovies()
        }

        searchEditText.setText(searchLine)

        searchIcon.setOnClickListener {
            if (searchEditText.visibility == View.VISIBLE) {
                searchEditText.visibility = View.GONE
            } else {
                searchEditText.visibility = View.VISIBLE
            }
        }

        searchEditText.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {

                if (event.action == KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER
                ) {

                    if (searchEditText.text.toString() == "") {
                        Toast.makeText(context, "Please, enter search line", Toast.LENGTH_SHORT)
                            .show()

                    } else {
                        coroutineScope.launch {
                            searchRequest = searchEditText.text.toString()
                            movies = SearchRequestAPICreator().getMovies(searchRequest)
                            movies.sortByDescending { it.rating }
                            withContext(Dispatchers.Main) {
                                searchEditText.setText("")
                                searchEditText.visibility = View.GONE
                            }

                            (activity as MainActivity).changeMoviesList(
                                movies,
                                searchRequest
                            )
                        }
                    }

                    return true
                }
                return false
            }
        })


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

        if (arguments == null) {
            listenMoviesData(recyclerView)
        }

        return view
    }

    private fun listenMoviesData(recyclerView: RecyclerView) {
        viewModel.loadingMovies.observe(viewLifecycleOwner, Observer {
            movies = viewModel.loadingMovies.value ?: mutableListOf()
            (recyclerView.adapter as MoviesListAdapter).movies = movies
            (recyclerView.adapter as MoviesListAdapter).notifyDataSetChanged()
        })
    }

}

const val MOVIES_KEY = "current_movies"
const val SEARCH_LINE_KEY = "search_line"