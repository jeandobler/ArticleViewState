package com.dobler.articleviewstate

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository {
    suspend fun getPage() = withContext(Dispatchers.IO) {
        listOf(Movie("t1"), Movie("t2"), Movie("t3"))
    }
}
