package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AnosAuraColorScheme = darkColorScheme(
    primary = AuraRed,
    onPrimary = Color.White,
    primaryContainer = AuraRedDark,
    onPrimaryContainer = AuraGold,
    secondary = AuraFlameOrange,
    onSecondary = Color.Black,
    secondaryContainer = AuraSurfaceVariant,
    onSecondaryContainer = AuraGold,
    tertiary = AuraCyan,
    onTertiary = Color.Black,
    background = AuraBackground,
    onBackground = TextWhite,
    surface = AuraSurface,
    onSurface = TextWhite,
    surfaceVariant = AuraSurfaceVariant,
    onSurfaceVariant = TextMutedGray,
    outline = AuraBorder,
    outlineVariant = AuraRed.copy(alpha = 0.5f),
    error = HeadshotRed,
    onError = Color.White
)

@Composable
fun AnosAuraTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = AnosAuraColorScheme,
        typography = Typography,
        content = content
    )
}
