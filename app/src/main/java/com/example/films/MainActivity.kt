package com.example.films

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity(),
    FragmentMoviesList.TransactionsFragmentMLClicks,
    FragmentMoviesDetails.TransactionsFragmentMDClicks {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.supportActionBar?.hide()
    }

    override fun filmOnClick() {

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, FragmentMoviesDetails())
            .addToBackStack(null)
            .commit()
    }

    override fun backOnClick() {
        supportFragmentManager.popBackStack()
    }
}