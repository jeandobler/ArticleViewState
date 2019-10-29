package com.dobler.articleviewstate.solution

import com.dobler.articleviewstate.Movie

sealed class MovieViewState
class Movies(val result: List<Movie>) : MovieViewState()
class LongToast(val message: String) : MovieViewState()
class GenericError(val message: String) : MovieViewState()

object ShowLoading : MovieViewState()
object EmptyResults : MovieViewState()
