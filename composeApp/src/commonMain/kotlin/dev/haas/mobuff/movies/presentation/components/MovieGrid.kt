package dev.haas.mobuff.movies.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.haas.mobuff.movies.domain.model.Movie

@Composable
fun MovieGrid(moviesList: List<Movie>, onClick: (Movie) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.padding(2.dp)
    ) {
        items(moviesList.size) { index ->
            MovieItem(moviesList[index], onClick = { movie ->
                onClick(movie)
            })
        }
    }
}