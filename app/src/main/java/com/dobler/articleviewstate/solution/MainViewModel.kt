package com.dobler.articleviewstate.solution

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dobler.articleviewstate.MovieRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MovieRepository) : ViewModel() {

    private val movieState = MutableLiveData<MovieViewState>()
    val movieViewState: LiveData<MovieViewState> = movieState

    fun loadMovies() {

        movieState.value = ShowLoading

        viewModelScope.launch {
            try {
                val response = repository.getPage()
                response.let {
                    if (it.isEmpty()) {
                        movieState.postValue(EmptyResults)
                    } else {
                        movieState.postValue(Movies(it))
                    }
                }
            } catch (e: Exception) {
                movieState.postValue(GenericError(e.message.toString()))
            }
        }
    }
}
