package com.dobler.articleviewstate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)


        viewModel.loadMovies()

        viewModel.movies
            .observe(this, Observer {
                binding.tvNoRepositories.visibility = View.GONE
                adapter.submitList(it.response)
                hideLoading()
            })


        setContentView(R.layout.activity_main)
    }
}
