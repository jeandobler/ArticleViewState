https://thoughtbot.com/blog/android-networking-view-state

https://engineering.udacity.com/modeling-viewmodel-states-using-kotlins-sealed-classes-a5d415ed87a7



Controlando ViewStates na ViewModel


Por que fazer assim?

Vamos pegar um exemplo basico de uma chamada simples de uma API de filmes.

`ViewModel`

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


`MainActivity`

   viewModel.loadMovies1()
    showLoading() // Iniciamos o load aqui

    viewModel.movies
        .observe(this, Observer {
            updateMovieList(it)
            hideLoading() // Remover o Load
        })


Essa é uma chamada de um fluxo simples onde a View precisa apenas mostrar os dados,
sem que haja interações extras com componentes da tela.
Porém, e se quisermos mostrar uma mensagme de erro controlada pela ViewModel  como fariamos?



`ViewModel`

    private val movieResult = MutableLiveData<List<Movie>>()
    val movies = movieResult

    private val responseError = MutableLiveData<String>()
    val error = responseError

    private fun loadMovies() {
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

`MainActivity`

    viewModel.loadMovies2()

    viewModel.movies
        .observe(this, Observer {
            updateMovieList(it)
            hideErrorMessage()
        })

    viewModel.error
        .observe(this, Observer {
            showErrorMessage(it)
        })

Começou a ficar bagunçado não é? agora a View vai ter que ter 2 observers separados,
mas não para por ai, caso queiramos adicionar mais ações para a View como mostrar um Toast e etc
teremos que criar mais observers? ou tiramos a lógica do ViewModel e colocamos na View?

Existe um jeito muito simples de controlar esses "estados da tela" usando SealedClass

Pimeiro criamos uma SealedClass que vai representar os estados da View,
em nosso caso como estamos na tela de Movie vamos criar a MovieViewState(sim criamos um state para cada view)


`MovieViewState`
    sealed class MovieViewState
    class Movies(val result: Movie) : MovieViewState()
    class LongToast (val message: String): MovieViewState()
    class GenericError (val message: String): MovieViewState()

    object ShowMovieLoading : MovieViewState()
    object EmptyMovieResults : MovieViewState()

Agora dentro da ViewModel vamos instanciar o MutableLiveData<MovieViewState>

`ViewModel`

    private val movieResult = MutableLiveData<MovieViewState>()
    val movies = movieResult

    private fun loadMovies() {

        movies.value =  ShowMovieLoading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getPage()
                response.let {
                    if (it.items.isEmpty()) {
                        movieResult.postValue(EmptyMovieResults)
                    } else {
                        movieResult.postValue(Movies(it.items))
                    }
                }
            } catch (e: Exception) {
                movieResult.postValue(GenericError(e.message.toString()))
            }
        }
    }


`MainActivity`

    viewModel.githubRepositories.observe(this,
    Observer {
        when (it) {
            is Results -> {
                binding.tvNoRepositories.visibility = View.GONE
                adapter.submitList(it.response)
                hideLoading()
            }
            is EmptyResults -> {
                binding.tvNoRepositories.visibility = View.VISIBLE
                hideLoading()
            }
            is Error -> {
                binding.tvNoRepositories.visibility = View.VISIBLE
                hideLoading()
                Log.e("Bro", it.error)
            }
            is ShowLoading -> {
                showLoading()
            }
        }
    })


Ficou bem mais simples não é mesmo? Se você tiver uma aplicação
simples sem muitas mudanças na View também pode criar uma SealedClass generica

`NetworkViewState`

    sealed class ResponseState<T>

    class Success<T>(val data: T) : ResponseState<T>()
    class Error<T>(val message: String) : ResponseState<T>()
    class Loading<T> : ResponseState<T>()



