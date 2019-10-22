package com.dobler.articleviewstate

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository = MovieRepository()
    private val movieResult = MutableLiveData<List<Movie>>()
    val movies = movieResult

    private fun loadMovies1() {
        viewModelScope.launch(Dispatchers.IO) {
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
