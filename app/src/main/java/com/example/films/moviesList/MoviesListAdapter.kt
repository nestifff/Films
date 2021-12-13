package com.example.films.moviesList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.films.R
import com.example.films.model.dataClasses.Movie


class MoviesListAdapter(
    context: Context,
    var movies: List<Movie>,
    private val clickListener: MoviesListClickListener?
) : RecyclerView.Adapter<MoviesListViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getItemCount() = movies.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesListViewHolder {
        return MoviesListViewHolder(
            inflater.inflate(
                R.layout.view_holder_movie,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MoviesListViewHolder, position: Int) {
        holder.bind(movies[position])
        holder.itemView.setOnClickListener {
            clickListener?.movieOnClick(movies[position])
        }
    }

}

class MoviesListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val tvName: TextView = view.findViewById(R.id.tv_movies_list_name)
    private val tvAgeLimit: TextView = view.findViewById(R.id.tv_movies_list_age_limit)
    private val tvGenre: TextView = view.findViewById(R.id.tv_movies_list_genre)
    private val tvNumOfReviews: TextView = view.findViewById(R.id.tv_movies_list_number_of_reviews)
    private val rbRating: RatingBar = view.findViewById(R.id.rb_movie_details_rating)
    private val ivMoviePoster: ImageView = view.findViewById(R.id.iv_movies_list_movie_poster)
    private val ivIsLiked: ImageView = view.findViewById(R.id.iv_movies_list_like)
    private val vIsLikedBackground: View = view.findViewById(R.id.iv_movies_list_background_like)
    private val tvRating: TextView = view.findViewById(R.id.tv_movies_list_rating)

    fun bind(movie: Movie) {

        tvName.text = movie.title
        tvAgeLimit.text = "${movie.age}+"

        val strBuilt: StringBuilder = java.lang.StringBuilder()
        for ((index, genre) in movie.genres.withIndex()) {
            val name: String = genre.name
            strBuilt.append(
                when (index) {
                    (movie.genres.size - 1) -> name
                    else -> "$name, "
                }
            )
        }
        tvGenre.text = strBuilt.toString()

        tvNumOfReviews.text = "${movie.reviewCount} rev."
        rbRating.rating = movie.rating / 2
        tvRating.text = movie.rating.toString()

        Glide.with(itemView.context)
            .load(movie.posterImageUrl)
            .into(ivMoviePoster)

        if (movie.isLiked) {
            ivIsLiked.setImageResource(R.drawable.like_active)
        } else {
            ivIsLiked.setImageResource(R.drawable.like_inactive)
        }

        vIsLikedBackground.setOnClickListener {
            if (movie.isLiked) {
                ivIsLiked.setImageResource(R.drawable.like_inactive)
                movie.isLiked = false
            } else {
                ivIsLiked.setImageResource(R.drawable.like_active)
                movie.isLiked = true
            }
        }
    }
}

interface MoviesListClickListener {
    fun movieOnClick(movie: Movie)
}