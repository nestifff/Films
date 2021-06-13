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
import java.io.Serializable


class FragmentMoviesDetails : Fragment() {

    private var listener: TransactionsFragmentMDClicks? = null
    var movie: Movie? = null
    private var movieDetails: MovieDetails? = null

    private lateinit var viewModel: MovieDetailsViewModel

    private lateinit var rvActorsList: RecyclerView

    private lateinit var tvBudget: TextView
    private lateinit var tvRevenue: TextView
    private lateinit var tvData: TextView
    private lateinit var tvTagline: TextView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        movie = arguments?.getSerializable(MOVIE_IN_BUNDLE_FRAGMENT) as Movie?
        //movieDetails = arguments?.getSerializable(MOVIE_DETAILS_IN_BUNDLE_FRAGMENT) as MovieDetails?

        //if (movieDetails == null) {
        movie?.let {
            viewModel.loadMovie(it)
        }

        viewModel.loadingMovie.observe(viewLifecycleOwner, Observer {
            movieDetails = it
            Log.i(TAG, it.toString())
            detailsUploadedUpdate()
        })

        /*} else {
            detailsUploadedUpdate()
        }*/

        val view = inflater.inflate(R.layout.fragment_movie_details, container, false)

        val ivPoster: ImageView = view.findViewById(R.id.iv_movie_details_picture_background_top)
        val tvAgeLimit: TextView = view.findViewById(R.id.tv_movie_details_age_limit)
        val tvName: TextView = view.findViewById(R.id.tv_movie_details_name)
        val tvGenre: TextView = view.findViewById(R.id.tv_movie_details_genre)
        val tvNumOfReviews: TextView = view.findViewById(R.id.tv_movie_details_number_of_reviews)
        val rbRating: RatingBar = view.findViewById(R.id.rb_movie_details_rating)
        val tvRating: TextView = view.findViewById(R.id.tv_movie_details_rating)
        val tvStoryline: TextView = view.findViewById(R.id.tv_movie_details_storyline)

        rvActorsList = view.findViewById(R.id.rv_movie_details_actors_list)
        tvBudget = view.findViewById(R.id.tv_movie_details_budget)
        tvRevenue = view.findViewById(R.id.tv_movie_details_revenue)
        tvData = view.findViewById(R.id.tv_movie_details_release_data)
        tvTagline = view.findViewById(R.id.tv_movie_details_tagline)

        tvBudget.visibility = View.GONE
        tvRevenue.visibility = View.GONE
        tvData.visibility = View.GONE
        tvTagline.visibility = View.GONE

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
        tvRating.text = movie?.rating.toString()

        view.findViewById<TextView>(R.id.tv_movie_details_button_back).apply {
            setOnClickListener {
                listener?.backOnClick()
            }
        }


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

        if (movieDetails != null) {

            (rvActorsList.adapter as ActorsListAdapter).updateActors(
                movieDetails?.actors ?: listOf()
            )

            if(movieDetails?.budget != 0.toLong()) {
                tvBudget.visibility = View.VISIBLE
                tvBudget.text = "Budget:   ${movieDetails?.budget}"
            }
            if (movieDetails?.revenue != 0.toLong()) {
                tvRevenue.visibility = View.VISIBLE
                tvRevenue.text = "Revenue: ${movieDetails?.revenue}"
            }

            tvData.visibility = View.VISIBLE
            tvTagline.visibility = View.VISIBLE

            tvData.text = "Release data: ${movieDetails?.releaseDate}"
            tvTagline.text = movieDetails?.tagline

            //this.arguments?.putSerializable(MOVIE_DETAILS_IN_BUNDLE_FRAGMENT, movieDetails)

        } else {
            Toast.makeText(context, "Network is not stable, can't load all data", Toast.LENGTH_LONG)
                .show()
            view?.findViewById<TextView>(R.id.cast_head_text)?.visibility = View.GONE
            rvActorsList.visibility = View.GONE
        }


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
//const val MOVIE_DETAILS_IN_BUNDLE_FRAGMENT = "movieDetails"