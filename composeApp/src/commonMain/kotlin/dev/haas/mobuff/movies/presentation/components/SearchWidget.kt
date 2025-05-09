package dev.haas.mobuff.movies.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchWidget(
    initialQuery: String = "",
    pref: (String) -> Unit
) {
    var movie by remember { mutableStateOf(initialQuery) }
    Column {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            value = movie,
            onValueChange = {
                movie = it
            },
            label = { Text("Search For a Movie") },
            trailingIcon = {
                Button(onClick = {
                    pref(movie)
                }) {
                    Icon(Icons.Outlined.Search, contentDescription = null)
                }
            }
        )
    }
}
