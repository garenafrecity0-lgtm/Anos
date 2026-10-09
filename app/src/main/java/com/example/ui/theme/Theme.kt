package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val PythonDarkColorScheme = darkColorScheme(
    primary = PythonGold,
    onPrimary = Color.Black,
    primaryContainer = PythonBlueDark,
    onPrimaryContainer = PythonYellow,
    secondary = PythonBlue,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF132A3E),
    onSecondaryContainer = PythonBlueLight,
    tertiary = SuccessEmerald,
    onTertiary = Color.Black,
    background = IdeBackground,
    onBackground = TextWhite,
    surface = IdeSurface,
    onSurface = TextWhite,
    surfaceVariant = IdeSurfaceVariant,
    onSurfaceVariant = TextMutedGray,
    outline = IdeBorder,
    outlineVariant = PythonBlue.copy(alpha = 0.4f),
    error = ErrorCrimson,
    onError = Color.White
)

@Composable
fun AnosPyTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = PythonDarkColorScheme,
        typography = Typography,
        content = content
    )
}
