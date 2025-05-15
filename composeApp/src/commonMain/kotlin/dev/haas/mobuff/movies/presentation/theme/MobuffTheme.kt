package dev.haas.mobuff.movies.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF0F52BA),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD1E1FF),
    onPrimaryContainer = Color(0xFF001D36),
    secondary = Color(0xFFFF6B00),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFFDCC3),
    onSecondaryContainer = Color(0xFF2C1600),
    tertiary = Color(0xFF6750A4),
    background = Color(0xFFF8F9FA),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1A1C1E),
    surfaceVariant = Color(0xFFE0E2EC),
    onSurfaceVariant = Color(0xFF44474E)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF9ECAFF),
    onPrimary = Color(0xFF003258),
    primaryContainer = Color(0xFF00497D),
    onPrimaryContainer = Color(0xFFD1E4FF),
    secondary = Color(0xFFFFB77C),
    onSecondary = Color(0xFF492900),
    secondaryContainer = Color(0xFF6A3C00),
    onSecondaryContainer = Color(0xFFFFDCC3),
    tertiary = Color(0xFFCFBCFF),
    background = Color(0xFF1A1C1E),
    surface = Color(0xFF111316),
    onSurface = Color(0xFFE3E2E6),
    surfaceVariant = Color(0xFF43474E),
    onSurfaceVariant = Color(0xFFC3C7CF)
)

// Create a properly working global theme state with a flag to track if initialization has happened
object ThemeManager {
    private val _isDarkTheme = mutableStateOf(false)
    val isDarkTheme: State<Boolean> = _isDarkTheme

    // Track whether the theme has been explicitly set by the user
    private var hasBeenInitialized = false

    fun toggleTheme() {
        _isDarkTheme.value = !_isDarkTheme.value
        hasBeenInitialized = true
    }

    fun setDarkTheme(isDark: Boolean) {
        // Only set the theme from the system if it hasn't been explicitly set by the user
        if (!hasBeenInitialized) {
            _isDarkTheme.value = isDark
        }
    }
}

val LocalThemeMode = staticCompositionLocalOf { ThemeManager }

@Composable
fun MobuffTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // This will only apply the system theme on the first composition
    ThemeManager.setDarkTheme(isDarkTheme)

    val colorScheme = if (ThemeManager.isDarkTheme.value) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    CompositionLocalProvider(LocalThemeMode provides ThemeManager) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }
}

val Typography = androidx.compose.material3.Typography()

