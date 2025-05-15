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
    primary = Color(0xFFFF5722),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFEDE8),
    onPrimaryContainer = Color(0xFF3F1400),
    secondary = Color(0xFFFF9800),
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFFFFECCF),
    onSecondaryContainer = Color(0xFF3D2600),
    tertiary = Color(0xFFFF3D00),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFFDBD0),
    onTertiaryContainer = Color(0xFF3F0D00),
    background = Color(0xFFFFFBF8),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFFF3E5D7),
    onSurfaceVariant = Color(0xFF4E4539),
    error = Color(0xFFD83B01),
    outline = Color(0xFF847467)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFFF7D47),
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF8B3000),
    onPrimaryContainer = Color(0xFFFFEDE8),
    secondary = Color(0xFFFFB74D),
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF7F4F00),
    onSecondaryContainer = Color(0xFFFFECCF),
    tertiary = Color(0xFFFF6E40),
    onTertiary = Color.Black,
    tertiaryContainer = Color(0xFF8C2100),
    onTertiaryContainer = Color(0xFFFFDBD0),
    background = Color(0xFF1E1410),
    surface = Color(0xFF2E211A),
    onSurface = Color(0xFFEDE0D4),
    surfaceVariant = Color(0xFF3E2E22),
    onSurfaceVariant = Color(0xFFD7C2B2),
    error = Color(0xFFFF6D42),
    outline = Color(0xFFA48E7C)
)

object ThemeManager {
    private val _isDarkTheme = mutableStateOf(false)
    val isDarkTheme: State<Boolean> = _isDarkTheme
    private var hasBeenInitialized = false

    fun toggleTheme() {
        _isDarkTheme.value = !_isDarkTheme.value
        hasBeenInitialized = true
    }

    fun setDarkTheme(isDark: Boolean) {
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
