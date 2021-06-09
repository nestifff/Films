package com.example.films.movieDetails

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.films.model.dataClasses.Movie
import com.example.films.R
import com.example.films.TAG
import com.example.films.model.dataClasses.MovieDetails
import com.example.films.moviesList.MoviesListAdapter
import com.example.films.moviesList.MoviesListClickListener
import com.example.films.moviesList.MoviesListViewModel


class FragmentMoviesDetails : Fragment() {

    private var listener: TransactionsFragmentMDClicks? = null
    var movie: Movie? = null
    lateinit var movieDetails: MovieDetails

    private lateinit var viewModel: MovieDetailsViewModel

    private lateinit var rvActorsList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        movie = arguments?.getSerializable(MOVIE_IN_BUNDLE_FRAGMENT) as Movie?
        movie?.let {
            viewModel.loadMovie(it)
        }

        val view = inflater.inflate(R.layout.fragment_movie_details, container, false)

        val ivPoster: ImageView = view.findViewById(R.id.iv_movie_details_picture_background_top)
        val tvAgeLimit: TextView = view.findViewById(R.id.tv_movie_details_age_limit)
        val tvName: TextView = view.findViewById(R.id.tv_movie_details_name)
        val tvGenre: TextView = view.findViewById(R.id.tv_movie_details_genre)
        val tvNumOfReviews: TextView = view.findViewById(R.id.tv_movie_details_number_of_reviews)
        val rbRating: RatingBar = view.findViewById(R.id.rb_movie_details_rating)
        val tvStoryline: TextView = view.findViewById(R.id.tv_movie_details_storyline)

        rvActorsList = view.findViewById(R.id.rv_movie_details_actors_list)

        tvAgeLimit.text = "${movie?.age ?: 0}+"
        tvName.text = movie?.title ?: "---"

        Glide.with(view.context)
            .load(movie?.backgroundImageUrl)
            .into(ivPoster)

        val strBuilt: StringBuilder = java.lang.StringBuilder()
        for ((index, genre) in movie!!.genres.withIndex()) {
            val name: String = genre.name
            strBuilt.append(
                when (index) {
                    (movie!!.genres.size - 1) -> name
                    else -> "$name, "
                }
            )
        }

        tvGenre.text = strBuilt.toString()

        tvNumOfReviews.text = "${movie?.reviewCount} reviews"
        tvStoryline.text = movie?.storyLine
        rbRating.rating = (movie?.rating ?: 0f) / 2

        view.findViewById<TextView>(R.id.tv_movie_details_button_back).apply {
            setOnClickListener {
                listener?.backOnClick()
            }
        }

        viewModel.loadingMovie.observe(viewLifecycleOwner, Observer {
            movieDetails = it
            Log.i(TAG, it.toString())
            detailsUploadedUpdate()
        })

        val adapter = ActorsListAdapter(
            view.context,
            listOf()
        )
        rvActorsList.adapter = adapter

        rvActorsList.layoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.HORIZONTAL, false
        )

        return view
    }

    private fun detailsUploadedUpdate() {
        Toast.makeText(activity, "Details is uploaded!", Toast.LENGTH_SHORT).show()
        (rvActorsList.adapter as ActorsListAdapter).updateActors(movieDetails.actors)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is TransactionsFragmentMDClicks) {
            listener = context
        }
        viewModel =
            ViewModelProviders.of(this@FragmentMoviesDetails)
                .get(MovieDetailsViewModel::class.java)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface TransactionsFragmentMDClicks {
        fun backOnClick()
    }

}

const val MOVIE_IN_BUNDLE_FRAGMENT = "movie"