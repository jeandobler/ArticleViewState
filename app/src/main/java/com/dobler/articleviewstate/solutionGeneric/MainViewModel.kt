package com.dobler.articleviewstate.solutionGeneric

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dobler.articleviewstate.Movie
import com.dobler.articleviewstate.MovieRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    var repository: MovieRepository = MovieRepository()

    private val movieState = MutableLiveData<NetworkViewState<List<Movie>>>()
    val movieViewState: LiveData<NetworkViewState<List<Movie>>> = movieState

    fun loadMovies() {
        movieState.value = Loading()
        viewModelScope.launch {
            try {
                val response = repository.getPage()
                response.let {
                    movieState.value = Success(it)
                }
            } catch (e: Exception) {
                movieState.postValue(Error(e.message.toString()))
            }
        }
    }
}
