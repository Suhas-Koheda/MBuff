package dev.haas.mobuff.movies.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Filters(
    initialLanguage: String = "te",
    initialPage: Int = 1,
    languageFun: (String) -> Unit,
    pageFun: (Int) -> Unit
) {
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

    var selectedLanguage by remember { mutableStateOf(initialLanguage) }
    var page by remember { mutableStateOf(initialPage) }
    var lastPage by remember { mutableStateOf(10) }

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = "Language",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            color = MaterialTheme.colorScheme.primary
        )
        
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(indianLanguages.size) { index ->
                val language = indianLanguages[index]
                ElevatedFilterChip(
                    selected = selectedLanguage == language.second,
                    onClick = {
                        selectedLanguage = language.second
                        languageFun(language.second)
                        page = 1
                    },
                    label = { Text(language.first) },
                    modifier = Modifier.padding(end = 8.dp),
                    colors = FilterChipDefaults.elevatedFilterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }
        }

        Text(
            text = "Page",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            color = MaterialTheme.colorScheme.primary
        )
        
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(10) { i ->
                if (i != 0) {
                    ElevatedFilterChip(
                        selected = page == i,
                        onClick = {
                            page = i
                            pageFun(page)
                        },
                        label = { Text("$i") },
                        modifier = Modifier.padding(end = 8.dp),
                        colors = FilterChipDefaults.elevatedFilterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                }
            }
            item {
                ElevatedFilterChip(
                    onClick = {
                        page = lastPage
                        pageFun(lastPage)
                        lastPage++
                    },
                    label = { Text("Next") },
                    modifier = Modifier.padding(end = 8.dp),
                    selected = false,
                    colors = FilterChipDefaults.elevatedFilterChipColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        labelColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                )
            }
        }
    }
}
