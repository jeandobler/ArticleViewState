package com.dobler.articleviewstate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        setContentView(R.layout.activity_main)

        viewModel.loadMovies1()

        viewModel.movies
            .observe(this, Observer {
                updateMovieList(it)
            })


    }

    fun updateMovieList(movies: List<Movie>) {

    }
}
