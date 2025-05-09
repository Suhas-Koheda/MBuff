package dev.haas.mobuff.movies.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Filters(language: (String) -> Unit, page: (Int) -> Unit) {
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
                        //TODO:
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
                            //TODO:
                        },
                        label = { Text("$i") },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }
            item {
                FilterChip(
                    onClick = {
                        //TODO:
                    },
                    label = { Text("Next") },
                    modifier = Modifier.padding(end = 8.dp),
                    selected = page == 10,
                )
            }
        }
    }
}