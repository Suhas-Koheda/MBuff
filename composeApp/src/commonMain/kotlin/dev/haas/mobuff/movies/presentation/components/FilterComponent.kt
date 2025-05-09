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
fun Filters(languageFun: (String) -> Unit, pageFun: (Int) -> Unit) {
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

    var selectedLanguage by remember { mutableStateOf(indianLanguages[0].second) }
    var page by remember { mutableStateOf(1) }
    var lastPage by remember { mutableStateOf(10) }
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
                        languageFun(language.second)
                        page = 1
                    },
                    label = { Text(language.first) },
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
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
                            pageFun(page)
                        },
                        label = { Text("$i") },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }
            item {
                FilterChip(
                    onClick = {
                        pageFun(lastPage)
                        lastPage++
                    },
                    label = { Text("Next") },
                    modifier = Modifier.padding(end = 8.dp),
                    selected = page == 10,
                )
            }
        }
    }
}