package com.dobler.articleviewstate.simpleproblem

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dobler.articleviewstate.Movie
import com.dobler.articleviewstate.R


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        setupMovies()
    }

    fun setupMovies() {
        viewModel.loadMovies()
        showLoading()

        viewModel.movies
            .observe(this, Observer {
                updateMovieList(it)
                hideLoading()
            })
    }

    fun updateMovieList(movies: List<Movie>) {}
    fun hideLoading() {}
    fun showLoading() {}
    fun showErrorMessage(error: String) {}
    fun hideErrorMessage() {}
}
