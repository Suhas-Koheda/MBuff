package dev.haas.mobuff.movies.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.haas.mobuff.movies.data.repository.TMDBRepository
import dev.haas.mobuff.movies.presentation.components.MovieGrid
import dev.haas.mobuff.movies.presentation.components.SearchWidget
import dev.haas.mobuff.movies.presentation.viewmodel.MovieViewModel

class MovieScreen : Screen {
    companion object {
        private var savedQuery = ""
        private var savedLanguage = "te"
        private var savedPage = 1
    }

    @Composable
    override fun Content() {
        val movieViewModel: MovieViewModel = remember {
            MovieViewModel(TMDBRepository.instance)
        }

        var selectedLanguage by remember { mutableStateOf(savedLanguage) }
        var page by remember { mutableStateOf(savedPage) }
        var movieQuery by remember { mutableStateOf(savedQuery) }
        val navigator = LocalNavigator.currentOrThrow
        if (savedQuery.isNotEmpty()) {
            remember {
                movieViewModel.fetchMovies(
                    query = savedQuery,
                    language = savedLanguage,
                    page = savedPage
                )
                true
            }
        }
        
        Column {
            SearchWidget(
                initialQuery = movieQuery,
                pref = { movie ->
                    movieQuery = movie
                    savedQuery = movie
                    savedLanguage = selectedLanguage
                    savedPage = page

                    movieViewModel.fetchMovies(
                        query = movieQuery,
                        language = selectedLanguage,
                        page = page
                    )
                }
            )
            val movieState = movieViewModel.movieState.collectAsState()
            when (movieState.value) {
                is MovieViewModel.MovieState.Error -> {
                    Text((movieState.value as MovieViewModel.MovieState.Error).message)
                }

                MovieViewModel.MovieState.Loading -> {
                    CircularProgressIndicator()
                }

                is MovieViewModel.MovieState.Success -> {
                    val moviesGrid = (movieState.value as MovieViewModel.MovieState.Success).movies
                    MovieGrid(moviesGrid, onClick = { movie ->
                        savedQuery = movieQuery
                        savedLanguage = selectedLanguage
                        savedPage = page

                        navigator.push(MovieDetailScreen(movie = movie))
                    })
                }
            }
        }
    }
}
