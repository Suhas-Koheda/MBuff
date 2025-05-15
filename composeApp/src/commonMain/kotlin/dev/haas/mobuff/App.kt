package dev.haas.mobuff

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import dev.haas.mobuff.movies.presentation.theme.MobuffTheme
import dev.haas.mobuff.movies.presentation.ui.MovieScreen

@Composable
fun App() {
    MobuffTheme {
        Navigator(MovieScreen())
    }
}
