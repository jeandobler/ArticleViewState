package com.dobler.articleviewstate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        setContentView(R.layout.activity_main)
    }

    fun setupMovies1() {
        viewModel.loadMovies1()
        showLoading() // Iniciamos o load aqui

        viewModel.movies
            .observe(this, Observer {
                updateMovieList(it)
                hideLoading() // Remover o Load
            })
    }


    fun setupMovies2() {
        viewModel.loadMovies2()

        viewModel.movies
            .observe(this, Observer {
                updateMovieList(it)
                hideErrorMessage()
            })

        viewModel.error
            .observe(this, Observer {
                showErrorMessage(it)
            })
    }

    fun updateMovieList(movies: List<Movie>) {}
    fun hideLoading() {}
    fun showLoading() {}
    fun showErrorMessage(error: String) {}
    fun hideErrorMessage() {}

}
