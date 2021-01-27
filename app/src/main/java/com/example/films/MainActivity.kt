package com.example.films

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity(),
    FragmentMoviesDetails.TransactionsFragmentMDClicks,
    MoviesListClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.supportActionBar?.hide()
    }


    override fun backOnClick() {
        supportFragmentManager.popBackStack()
    }

    override fun movieOnClick(movie: Movie) {

        val fragment = FragmentMoviesDetails()
        fragment.movie = movie

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}