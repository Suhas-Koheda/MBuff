package dev.haas.mobuff.movies.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import dev.haas.mobuff.movies.domain.model.Movie
import dev.haas.mobuff.movies.data.repository.TMDBRepository

data class LanguagePagePair(
    val name: String,
    val page: String
)

class MovieScreen: Screen{
    @Composable
    override fun Content() {
        val movieViewModel:MovieViewModel= remember {
            MovieViewModel(TMDBRepository.instance)
        }

        Column() {
            val movieList = movieViewModel.movieState.collectAsState()
            SearchWidget(movieViewModel)
            when (movieList.value) {
                is MovieViewModel.MovieState.Loading -> {
                    CircularProgressIndicator()
                }

                is MovieViewModel.MovieState.Success -> {
                    val movies = (movieList.value as MovieViewModel.MovieState.Success).movies
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 128.dp),
                        contentPadding = PaddingValues(8.dp),
                        modifier = Modifier.padding(2.dp)
                    ) {
                        items(movies.size) { index ->
                            MovieItem(movies[index])
                        }
                    }
                }

                is MovieViewModel.MovieState.Error -> {
                    Text((movieList.value as MovieViewModel.MovieState.Error).message)
                }
            }
        }
    }
}

@Composable
fun MovieItem(movie: Movie) {
    val navigator= LocalNavigator.currentOrThrow
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable { navigator.push(MovieDetailScreen(movie)) }
    ){
            AsyncImage(
                model = movie.posterPathUrl,
                contentDescription = null,
                onError = {
                    println("Image loading failed: ${it.result.throwable}")
                },
                onLoading = {
                    println("Loading")
                }
            )
            Text(movie.title)
    }
}

@Composable
fun SearchWidget(movieViewModel:MovieViewModel){
    var selectedLanguage by remember { mutableStateOf("te") }
    var page by remember { mutableStateOf(1) }
    Column {
        var movie by remember { mutableStateOf("") }
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            value=movie,
            onValueChange = {
                movie = it
            },
            label = { Text("Search For a Movie") },
            trailingIcon = {
              Button(onClick = {
                  movieViewModel.fetchMovies(movie,selectedLanguage,page)
              }) {
                  Icon(Icons.Outlined.Search, contentDescription = null)
              }
            }
        )
        Filters(
            movieViewModel = movieViewModel,
            selectedLanguage = selectedLanguage,
            onLanguageSelected = { newLang -> selectedLanguage = newLang },
            currentPage = page,
            onPageSelected = { newPage -> page = newPage }
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Filters(
    movieViewModel: MovieViewModel,
    selectedLanguage: String,
    onLanguageSelected: (String) -> Unit,
    currentPage: Int,
    onPageSelected: (Int) -> Unit
){
    val indianLanguages = listOf(
        Pair("Telugu", "te"),
        Pair("English", "en"),
        Pair("Hindi", "hi"),
        Pair("Bengali", "bn"),
        Pair("Tamil", "ta"),
        Pair("Marathi", "mr"),
        Pair("Gujarati", "gu"),
        Pair("Kannada", "kn"),
        Pair("Malayalam", "ml"),
        Pair("Punjabi", "pa"),
        Pair("Odia", "or"),
        Pair("Assamese", "as"),
        Pair("Urdu", "ur")
    )

    var lastPage=10
    var selectedLanguage by remember { mutableStateOf("te") }
    Column {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(indianLanguages.size) { index ->
                val language = indianLanguages[index]
                FilterChip(
                    selected = selectedLanguage == language.second,
                    onClick = {
                        selectedLanguage = language.second
                        movieViewModel.fetchMovies(language=language.second)
                    },
                    label = { Text(language.first) },
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
        var page by remember { mutableStateOf(1) }
            LazyRow(
                contentPadding = PaddingValues(horizontal = 8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(10) { i ->
                    if (i != 0) {
                        FilterChip(
                            selected = page == i,
                            onClick = {
                                page = i
                                movieViewModel.fetchMovies(language=selectedLanguage, page=i)
                            },
                            label = { Text("$i") },
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }
                }
                item(){
                    FilterChip(
                        onClick = {
                            movieViewModel.fetchMovies(language=selectedLanguage, page=lastPage++)
                        },
                        label = { Text("Next") },
                        modifier = Modifier.padding(end = 8.dp),
                        selected = page==10,
                    )
                }
        }
    }
}