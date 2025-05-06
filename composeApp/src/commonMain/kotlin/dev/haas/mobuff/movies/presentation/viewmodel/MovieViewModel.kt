package dev.haas.mobuff.movies.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.haas.mobuff.movies.data.repository.TMDBRepository
import dev.haas.mobuff.movies.domain.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieViewModel(val tmdbRepository: TMDBRepository): ViewModel(){

    sealed class MovieState{
        object Loading:MovieState()
        data class Success(val movies:List<Movie>):MovieState()
        data class Error(val message:String):MovieState()
    }

    private val _movieState= MutableStateFlow<MovieState>(MovieState.Loading)
    val movieState=_movieState.asStateFlow()


    fun fetchMovies(query: String="", language:String="te", page:Int=1) {
        viewModelScope.launch {
            _movieState.value=MovieState.Loading
            try {
                val movies=tmdbRepository.getMovie(query,language,page)
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