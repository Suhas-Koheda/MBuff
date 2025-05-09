package dev.haas.mobuff.movies.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import coil3.compose.AsyncImage
import dev.haas.mobuff.movies.domain.model.Movie

@Composable
fun MovieItem(movie: Movie, onClick: (Movie) -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                onClick(movie)
            }
    ) {
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