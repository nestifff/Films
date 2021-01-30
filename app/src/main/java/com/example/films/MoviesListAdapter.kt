package com.example.films

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class MoviesListAdapter(
    context: Context,
    var movies : List<Movie>,
    private val clickListener: MoviesListClickListener?
) : RecyclerView.Adapter<MoviesListViewHolder>() {

    private val inflater : LayoutInflater = LayoutInflater.from(context)

    override fun getItemCount() = movies.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesListViewHolder {
        return MoviesListViewHolder(inflater.inflate(R.layout.view_holder_movie, parent, false))
    }

    override fun onBindViewHolder(holder: MoviesListViewHolder, position: Int) {
        holder.bind(movies[position])
        holder.itemView.setOnClickListener {
            clickListener?.movieOnClick(movies[position])
        }
    }

}

class MoviesListViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    private val tvName : TextView = view.findViewById(R.id.tv_movies_list_name)
    private val tvAgeLimit : TextView = view.findViewById(R.id.tv_movies_list_age_limit)
    private val tvGenre : TextView = view.findViewById(R.id.tv_movies_list_genre)
    private val tvNumOfReviews : TextView = view.findViewById(R.id.tv_movies_list_number_of_reviews)
    private val tvLength : TextView = view.findViewById(R.id.tv_movies_list_length)
    private val rbRating : RatingBar = view.findViewById(R.id.rb_movie_details_rating)
    private val ivMoviePoster : ImageView = view.findViewById(R.id.iv_movies_list_movie_poster)
    private val ivIsLiked: ImageView = view.findViewById(R.id.iv_movies_list_like)

    fun bind(movie : Movie) {

        tvName.text = movie.name
        tvAgeLimit.text = "${movie.ageLimit}+"
        tvGenre.text = movie.genre
        tvNumOfReviews.text = "${movie.numOfReviews} reviews"
        tvLength.text = "${movie.length} min"
        rbRating.rating = movie.rating.toFloat()
        ivMoviePoster.setImageResource(movie.posterImageRecourse)

        if (movie.isLiked) {
            ivIsLiked.setImageResource(R.drawable.like_active)
        } else {
            ivIsLiked.setImageResource(R.drawable.like_inactive)
        }
    }
}

interface MoviesListClickListener {
    fun movieOnClick(movie: Movie)
}