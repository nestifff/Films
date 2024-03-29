package com.example.films.movieDetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.films.R
import com.example.films.model.dataClasses.Movie
import com.example.films.model.dataClasses.MovieDetails
import org.koin.androidx.viewmodel.ext.android.viewModel


class FragmentMoviesDetails : Fragment() {

    private var listener: TransactionsFragmentMDClicks? = null
    var movie: Movie? = null
    private var movieId: Int? = null
    private var movieDetails: MovieDetails? = null

    private val viewModel: MovieDetailsViewModel by viewModel()

    private lateinit var rvActorsList: RecyclerView

    private lateinit var tvBudget: TextView
    private lateinit var tvRevenue: TextView
    private lateinit var tvData: TextView
    private lateinit var tvTagline: TextView

    private lateinit var ivPoster: ImageView
    private lateinit var tvAgeLimit: TextView
    private lateinit var tvName: TextView
    private lateinit var tvGenre: TextView
    private lateinit var tvNumOfReviews: TextView
    private lateinit var rbRating: RatingBar
    private lateinit var tvRating: TextView
    private lateinit var tvStoryline: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        movieId = arguments?.getInt(MOVIE_ID_IN_BUNDLE_FRAGMENT) as Int
        movie = arguments?.getSerializable(MOVIE_IN_BUNDLE_FRAGMENT) as Movie?
        //movieDetails = arguments?.getSerializable(MOVIE_DETAILS_IN_BUNDLE_FRAGMENT) as MovieDetails?

        movieId = movie?.id ?: movieId

        //if (movieDetails == null) {
        movieId?.let {
            viewModel.loadMovie(it)
        }

        viewModel.loadingMovie.observe(viewLifecycleOwner, Observer {
            movieDetails = it
            detailsUploadedUpdate()
        })

        /*} else {
            detailsUploadedUpdate()
        }*/

        val view = inflater.inflate(R.layout.fragment_movie_details, container, false)

        ivPoster = view.findViewById(R.id.iv_movie_details_picture_background_top)
        tvAgeLimit = view.findViewById(R.id.tv_movie_details_age_limit)
        tvName = view.findViewById(R.id.tv_movie_details_name)
        tvGenre = view.findViewById(R.id.tv_movie_details_genre)
        tvNumOfReviews = view.findViewById(R.id.tv_movie_details_number_of_reviews)
        rbRating = view.findViewById(R.id.rb_movie_details_rating)
        tvRating = view.findViewById(R.id.tv_movie_details_rating)
        tvStoryline = view.findViewById(R.id.tv_movie_details_storyline)

        rvActorsList = view.findViewById(R.id.rv_movie_details_actors_list)
        tvBudget = view.findViewById(R.id.tv_movie_details_budget)
        tvRevenue = view.findViewById(R.id.tv_movie_details_revenue)
        tvData = view.findViewById(R.id.tv_movie_details_release_data)
        tvTagline = view.findViewById(R.id.tv_movie_details_tagline)

        tvBudget.visibility = View.GONE
        tvRevenue.visibility = View.GONE
        tvData.visibility = View.GONE
        tvTagline.visibility = View.GONE

        if (movie != null) {
            loadStartDataMovieNotNull(view)
        }

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

    private fun loadStartDataMovieNotNull(view: View) {

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
    }

    private fun detailsUploadedUpdate() {

        if (movieDetails != null) {

            if (movie == null) {

                tvAgeLimit.text = "${movieDetails?.age ?: 0}+"
                tvName.text = movieDetails?.title ?: "---"

                Glide.with(requireContext())
                    .load(movieDetails?.backgroundImageUrl)
                    .into(ivPoster)

                val strBuilt: StringBuilder = java.lang.StringBuilder()
                for ((index, genre) in movieDetails!!.genres.withIndex()) {
                    val name: String = genre.name
                    strBuilt.append(
                        when (index) {
                            (movieDetails!!.genres.size - 1) -> name
                            else -> "$name, "
                        }
                    )
                }

                tvGenre.text = strBuilt.toString()

                tvNumOfReviews.text = "${movieDetails?.reviewCount} reviews"
                tvStoryline.text = movieDetails?.storyLine
                rbRating.rating = (movieDetails?.rating ?: 0f) / 2
                tvRating.text = movieDetails?.rating.toString()
            }

            (rvActorsList.adapter as ActorsListAdapter).updateActors(
                movieDetails?.actors ?: listOf()
            )

            if (movieDetails?.budget != 0.toLong()) {
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
//        viewModel =
//            ViewModelProviders.of(this@FragmentMoviesDetails)
//                .get(MovieDetailsViewModel::class.java)
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
const val MOVIE_ID_IN_BUNDLE_FRAGMENT = "movieIDBundleFragment"
//const val MOVIE_DETAILS_IN_BUNDLE_FRAGMENT = "movieDetails"