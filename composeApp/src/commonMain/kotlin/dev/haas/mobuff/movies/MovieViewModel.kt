package dev.haas.mobuff.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.haas.mobuff.movies.model.Movie
import dev.haas.mobuff.movies.model.MovieLogic
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieViewModel(val movieLogic:MovieLogic):ViewModel(){

    sealed class MovieState{
        object Loading:MovieState()
        data class Success(val movies:List<Movie>):MovieState()
        data class Error(val message:String):MovieState()
    }

    private val _movieState= MutableStateFlow<MovieState>(MovieState.Loading)
    val movieState=_movieState.asStateFlow()
    private fun fetchMovies(){
        viewModelScope.launch {
            _movieState.value=MovieState.Loading
            try {
                val movies=movieLogic.getMovieList()
                _movieState.value=MovieState.Success(movies.results)
            }catch (e:Exception){
                _movieState.value=MovieState.Error(e.message.toString())
            }
        }
    }

    fun fetchPage(selectedLanguage:String,i: Int=1) {
        viewModelScope.launch {
            _movieState.value=MovieState.Loading
            try {
                val movies=movieLogic.getMovieList(language = selectedLanguage,page=i)
                _movieState.value=MovieState.Success(movies.results)
            }catch (e:Exception){
                _movieState.value=MovieState.Error(e.message.toString())
            }
        }
    }

    fun searchMovie(query: String,language:String,page:Int) {
        viewModelScope.launch {
            _movieState.value=MovieState.Loading
            try {
                val movies=movieLogic.searchMovie(query,language,page)
                _movieState.value=MovieState.Success(movies.results)
            }catch (e:Exception){
                _movieState.value=MovieState.Error(e.message.toString())
            }
        }
    }

    init{
        fetchMovies()
    }

}