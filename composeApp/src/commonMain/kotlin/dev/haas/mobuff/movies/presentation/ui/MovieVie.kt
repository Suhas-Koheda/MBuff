package dev.haas.mobuff.movies.presentation.ui

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import dev.haas.mobuff.movies.presentation.viewmodel.MovieViewModel

data class LanguagePagePair(
    val name: String,
    val page: String
)

class MovieScree : Screen {

    var moviePref: String = "te 1"

    @Composable
    override fun Content() {

        }
    }

    @Composable
    fun SearchWidget(movieViewModel: MovieViewModel) {

    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    fun Filters(
        movieViewModel: MovieViewModel,
        selectedLanguage: String,
        onLanguageSelected: (String) -> Unit,
        currentPage: Int,
        onPageSelected: (Int) -> Unit
    ) {

    }
