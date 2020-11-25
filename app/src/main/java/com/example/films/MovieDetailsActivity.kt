package com.example.films

import android.graphics.PorterDuff
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.widget.RatingBar
import androidx.appcompat.app.AppCompatActivity


class MovieDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        this.supportActionBar?.hide()

        /*val ratingBar = findViewById<RatingBar>(R.id.rating_bar)

        val stars = ratingBar.progressDrawable as LayerDrawable
        stars.getDrawable(2).setColorFilter(
            resources.getColor(R.color.pink),
            PorterDuff.Mode.SRC_ATOP
        )
        stars.getDrawable(1).setColorFilter(
            resources.getColor(R.color.pink),
            PorterDuff.Mode.SRC_ATOP
        )
        stars.getDrawable(0).setColorFilter(
            resources.getColor(R.color.gray),
            PorterDuff.Mode.SRC_ATOP
        )*/
    }
}