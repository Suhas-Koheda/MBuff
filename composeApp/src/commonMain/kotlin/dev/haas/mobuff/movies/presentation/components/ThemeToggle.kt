package dev.haas.mobuff.movies.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.haas.mobuff.movies.presentation.theme.LocalThemeMode

@Composable
fun ThemeToggleButton() {
    val themeManager = LocalThemeMode.current
    val isDarkTheme by themeManager.isDarkTheme

    IconButton(
        onClick = { themeManager.toggleTheme() },
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Text(
            text = if (isDarkTheme) "☀️" else "🌙",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.size(24.dp)
        )
    }
}
