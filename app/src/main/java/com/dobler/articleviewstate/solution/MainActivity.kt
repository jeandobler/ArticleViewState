package com.dobler.articleviewstate.solution

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

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        setContentView(R.layout.activity_main)
        setupMovies()
    }

    private fun setupMovies() {
        viewModel.loadMovies()
        viewModel.movieViewState.observe(this,
            Observer {
                when (it) {
                    is Movies -> {
                        hideErrorMessage()
                        updateMovieList(it.result)
                        hideLoading()
                    }
                    is EmptyResults -> {
                        showEmptyMessage()
                        hideLoading()
                    }
                    is GenericError -> {
                        showErrorMessage(it.message)
                        hideLoading()
                    }
                    is ShowLoading -> {
                        showLoading()
                    }
                }
            })
    }

    private fun updateMovieList(movies: List<Movie>) {}
    private fun hideLoading() {}
    private fun showLoading() {}
    private fun showErrorMessage(error: String) {}
    private fun showEmptyMessage() {}
    private fun hideErrorMessage() {}

}
