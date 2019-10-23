package com.dobler.articleviewstate

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MovieRepository) : ViewModel() {

    private val movieResult = MutableLiveData<List<Movie>>()
    val movies = movieResult

    fun loadMovies1() {
        viewModelScope.launch {
            try {
                val response = repository.getPage()
                response.let {
                    movieResult.postValue(it)
                }
            } catch (e: Exception) {
            }
        }
    }
}
