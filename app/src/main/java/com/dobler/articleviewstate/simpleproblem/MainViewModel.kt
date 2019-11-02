package com.dobler.articleviewstate.simpleproblem

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dobler.articleviewstate.Movie
import com.dobler.articleviewstate.MovieRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    var repository: MovieRepository = MovieRepository()

    private val movieResult = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = movieResult

    fun loadMovies() {
        viewModelScope.launch {
            try {
                val response = repository.getPage()
                response.let {
                    movieResult.postValue(it)
                }
            } catch (e: Exception) {
//                ...
            }
        }
    }
}

