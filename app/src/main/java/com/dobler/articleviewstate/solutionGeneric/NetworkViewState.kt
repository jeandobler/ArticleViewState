package com.dobler.articleviewstate.solutionGeneric

sealed class NetworkViewState<T>

class Success<T>(val data: T) : NetworkViewState<T>()
class Error<T>(val message: String) : NetworkViewState<T>()
class Loading<T> : NetworkViewState<T>()