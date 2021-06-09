package com.example.films.moviesList

import android.content.Context
import android.content.res.Configuration
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.films.R
import com.example.films.model.dataClasses.Movie


class FragmentMoviesList : Fragment() {

    private var listener: SearchMoviesOnClick? = null

    private var movies: MutableList<Movie> = mutableListOf()

    private val loadMoviesProvider: LoadMoviesProvider =
        LoadMoviesProvider()
    private var searchLine = ""

    private lateinit var searchRequest: String
    lateinit var searchEditText: EditText
    lateinit var tvFoundNum: TextView

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
        searchEditText = view.findViewById(R.id.et_search)

        val tvMoviesListName = view.findViewById<TextView>(R.id.tv_moviesNameOfMoviesList)
        tvFoundNum = view.findViewById(R.id.tv_moviesListFoundCount)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setTopMargin(tvMoviesListName, 6)
            setTopMargin(view.findViewById(R.id.iv_circles), 16)
            setTopMargin(recyclerView, 64)
        }

        if (arguments != null) {
            movies =
                arguments?.getSerializable(MOVIES_KEY) as MutableList<Movie>? ?: mutableListOf()
            searchLine = arguments?.getString(SEARCH_LINE_KEY) ?: ""
            searchEditText.visibility = View.GONE
            tvMoviesListName.text = "Search: \"$searchLine\""
            tvFoundNum.text = "Found: ${movies.size}"

        } else {
            viewModel.loadMovies()
        }

        searchEditText.setText(searchLine)

        searchIcon.setOnClickListener {

            if (searchEditText.visibility == View.VISIBLE) {
                searchEditText.visibility = View.GONE
                tvMoviesListName.visibility = View.VISIBLE
                tvFoundNum.visibility = View.VISIBLE

            } else {
                searchEditText.visibility = View.VISIBLE
                searchEditText.setText("")
                tvMoviesListName.visibility = View.GONE
                tvFoundNum.visibility = View.GONE
            }
        }

        searchEditText.setOnKeyListener(object : View.OnKeyListener {

            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {

                if (event.action == KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER
                ) {

                    if (searchEditText.text.toString() == "") {
                        Toast.makeText(
                            context,
                            "Please, enter search line",
                            Toast.LENGTH_SHORT
                        )
                            .show()

                    } else {
                        searchRequest = searchEditText.text.toString()
                        listener?.searchMoviesOnClick(searchRequest)
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
            tvFoundNum.text = "Found: ${movies.size}"
        })
    }

    private fun setTopMargin(view: View, topMargin: Int) {

        if (view.layoutParams is MarginLayoutParams) {

            val params = view.layoutParams as MarginLayoutParams
            params.setMargins(params.leftMargin, topMargin, params.rightMargin, params.bottomMargin)
            view.requestLayout()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SearchMoviesOnClick) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface SearchMoviesOnClick {
        fun searchMoviesOnClick(searchLine: String)
    }

}

const val MOVIES_KEY = "current_movies"
const val SEARCH_LINE_KEY = "search_line"