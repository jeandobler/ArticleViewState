package com.dobler.articleviewstate.complexproblem

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dobler.articleviewstate.Movie
import com.dobler.articleviewstate.MovieRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MovieRepository) : ViewModel() {

    private val movieResult = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = movieResult

    private val responseError = MutableLiveData<String>()
    val error: LiveData<String> = responseError


    fun loadMovies() {
        viewModelScope.launch {
            try {
                val response = repository.getPage()
                response.let {
                    movieResult.postValue(it)
                }
            } catch (e: Exception) {
                responseError.postValue("Error")
            }
        }
    }
}
