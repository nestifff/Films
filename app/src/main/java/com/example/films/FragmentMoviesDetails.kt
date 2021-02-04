package com.example.films

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.w3c.dom.Text


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
        val recyclerView: RecyclerView = view.findViewById(R.id.rv_movie_details_actors_list)

        val ivPoster: ImageView = view.findViewById(R.id.iv_movie_details_picture_background_top)
        val tvAgeLimit: TextView = view.findViewById(R.id.tv_movie_details_age_limit)
        val tvName: TextView = view.findViewById(R.id.tv_movie_details_name)
        val tvGenre: TextView = view.findViewById(R.id.tv_movie_details_genre)
        val tvNumOfReviews: TextView = view.findViewById(R.id.tv_movie_details_number_of_reviews)
        val rbRating: RatingBar = view.findViewById(R.id.rb_movie_details_rating)
        val tvStoryline: TextView = view.findViewById(R.id.tv_movie_details_storyline)

        tvAgeLimit.text = "${movie!!.pgAge}+"
        tvName.text = movie!!.title

        Glide.with(view.context)
            .load(movie!!.detailImageUrl)
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

        tvNumOfReviews.text = "${movie!!.reviewCount} reviews"
        tvStoryline.text = movie!!.storyLine
        rbRating.rating = movie!!.rating.toFloat()

        if (movie!!.actors.isEmpty()) {
            recyclerView.visibility = View.GONE
            view.findViewById<TextView>(R.id.cast_head_text).visibility = View.GONE

        } else {
            val adapter = ActorsListAdapter(view.context, movie!!.actors)
            recyclerView.adapter = adapter
            recyclerView.layoutManager =
                LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        }

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