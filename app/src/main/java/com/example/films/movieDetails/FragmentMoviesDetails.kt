package com.example.films.movieDetails

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.films.model.dataClasses.Movie
import com.example.films.R


class FragmentMoviesDetails : Fragment() {

    private var listener: TransactionsFragmentMDClicks? = null
    var movie: Movie? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        movie = arguments?.getSerializable(MOVIE_IN_BUNDLE_FRAGMENT) as Movie?

        val view = inflater.inflate(R.layout.fragment_movie_details, container, false)

        val ivPoster: ImageView = view.findViewById(R.id.iv_movie_details_picture_background_top)
        val tvAgeLimit: TextView = view.findViewById(R.id.tv_movie_details_age_limit)
        val tvName: TextView = view.findViewById(R.id.tv_movie_details_name)
        val tvGenre: TextView = view.findViewById(R.id.tv_movie_details_genre)
        val tvNumOfReviews: TextView = view.findViewById(R.id.tv_movie_details_number_of_reviews)
        val rbRating: RatingBar = view.findViewById(R.id.rb_movie_details_rating)
        val tvStoryline: TextView = view.findViewById(R.id.tv_movie_details_storyline)

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

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is TransactionsFragmentMDClicks) {
            listener = context
        }
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