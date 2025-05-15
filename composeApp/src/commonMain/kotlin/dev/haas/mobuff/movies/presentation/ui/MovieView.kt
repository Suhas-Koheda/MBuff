package dev.haas.mobuff.movies.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.haas.mobuff.movies.data.repository.TMDBRepository
import dev.haas.mobuff.movies.presentation.components.Filters
import dev.haas.mobuff.movies.presentation.components.MovieGrid
import dev.haas.mobuff.movies.presentation.components.SearchWidget
import dev.haas.mobuff.movies.presentation.components.ThemeToggleButton
import dev.haas.mobuff.movies.presentation.theme.MobuffTheme
import dev.haas.mobuff.movies.presentation.viewmodel.MovieViewModel

class MovieScreen : Screen {
    companion object {
        private var savedQuery = ""
        private var savedLanguage = "te"
        private var savedPage = 1
        private var hasInitialResults = false
    }

    @Composable
    override fun Content() {
        MobuffTheme {
            val movieViewModel: MovieViewModel = remember {
                MovieViewModel(TMDBRepository.instance)
            }

            var selectedLanguage by remember { mutableStateOf(savedLanguage) }
            var page by remember { mutableStateOf(savedPage) }
            var movieQuery by remember { mutableStateOf(savedQuery) }
            val navigator = LocalNavigator.currentOrThrow

            remember {
                if (savedQuery.isNotEmpty() || hasInitialResults) {
                    movieViewModel.fetchMovies(
                        query = savedQuery,
                        language = savedLanguage,
                        page = savedPage
                    )
                }
                true
            }

            Scaffold { innerPadding ->
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ThemeToggleButton()
                            SearchWidget(
                                initialQuery = movieQuery,
                                pref = { movie ->
                                    movieQuery = movie
                                    savedQuery = movie
                                    savedLanguage = selectedLanguage
                                    savedPage = page
                                    hasInitialResults = true

                                    movieViewModel.fetchMovies(
                                        query = movieQuery,
                                        language = selectedLanguage,
                                        page = page
                                    )
                                }
                            )
                        }

                        Filters(
                            initialLanguage = selectedLanguage,
                            initialPage = page,
                            languageFun = { language ->
                                selectedLanguage = language
                                savedLanguage = language
                                page = 1
                                savedPage = 1
                                hasInitialResults = true

                                movieViewModel.fetchMovies(
                                    query = movieQuery,
                                    language = selectedLanguage,
                                    page = page
                                )
                            },
                            pageFun = { newPage ->
                                page = newPage
                                savedPage = newPage
                                hasInitialResults = true

                                movieViewModel.fetchMovies(
                                    query = movieQuery,
                                    language = selectedLanguage,
                                    page = page
                                )
                            }
                        )

                        val movieState = movieViewModel.movieState.collectAsState()
                        when (movieState.value) {
                            is MovieViewModel.MovieState.Loading -> {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }

                            is MovieViewModel.MovieState.Error -> {
                                val error =
                                    (movieState.value as MovieViewModel.MovieState.Error).message
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        "Error: $error",
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                            }

                            is MovieViewModel.MovieState.Success -> {
                                val moviesGrid =
                                    (movieState.value as MovieViewModel.MovieState.Success).movies
                                MovieGrid(moviesGrid, onClick = { movie ->
                                    savedQuery = movieQuery
                                    savedLanguage = selectedLanguage
                                    savedPage = page
                                    hasInitialResults = true

                                    navigator.push(MovieDetailScreen(movie = movie))
                                })
                            }
                        }
                    }
                }
            }
        }
    }
}
