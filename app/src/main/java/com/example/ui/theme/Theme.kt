package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val NeonInfernoColorScheme = darkColorScheme(
    primary = FireOrange,
    onPrimary = Color.White,
    primaryContainer = FireOrangeDark,
    onPrimaryContainer = Color(0xFFFFDBCF),
    secondary = FireCrimson,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF450A0A),
    onSecondaryContainer = Color(0xFFFFCDD2),
    tertiary = FireGold,
    onTertiary = Color.Black,
    background = DarkObsidian,
    onBackground = TextPrimary,
    surface = DarkSurface,
    onSurface = TextPrimary,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = TextSecondary,
    outline = DarkBorder,
    outlineVariant = DarkBorderGlowing
)

private val CyberpunkColorScheme = darkColorScheme(
    primary = CyberCyan,
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF005B64),
    onPrimaryContainer = Color(0xFFB2F5EA),
    secondary = CyberGreen,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF054A28),
    onSecondaryContainer = Color(0xFFC6F6D5),
    tertiary = FireGold,
    onTertiary = Color.Black,
    background = CyberpunkBackground,
    onBackground = TextPrimary,
    surface = CyberpunkSurface,
    onSurface = TextPrimary,
    surfaceVariant = CyberpunkSurfaceVariant,
    onSurfaceVariant = TextSecondary,
    outline = CyberpunkBorder,
    outlineVariant = CyberCyan.copy(alpha = 0.4f)
)

private val RoyalAmethystColorScheme = darkColorScheme(
    primary = AmethystPrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF581C87),
    onPrimaryContainer = Color(0xFFF3E8FF),
    secondary = AmethystSecondary,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF701A75),
    onSecondaryContainer = Color(0xFFFAE8FF),
    tertiary = AmethystAccent,
    onTertiary = Color.Black,
    background = AmethystBackground,
    onBackground = TextPrimary,
    surface = AmethystSurface,
    onSurface = TextPrimary,
    surfaceVariant = AmethystSurfaceVariant,
    onSurfaceVariant = TextSecondary,
    outline = AmethystBorder,
    outlineVariant = AmethystPrimary.copy(alpha = 0.4f)
)

private val ObsidianGhostColorScheme = darkColorScheme(
    primary = BloodPrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF7F1D1D),
    onPrimaryContainer = Color(0xFFFFE4E6),
    secondary = BloodSecondary,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF450A0A),
    onSecondaryContainer = Color(0xFFFECDD3),
    tertiary = Color(0xFFFB923C),
    onTertiary = Color.Black,
    background = BloodBackground,
    onBackground = TextPrimary,
    surface = BloodSurface,
    onSurface = TextPrimary,
    surfaceVariant = BloodSurfaceVariant,
    onSurfaceVariant = TextSecondary,
    outline = BloodBorder,
    outlineVariant = BloodPrimary.copy(alpha = 0.4f)
)

private val FrostArcticColorScheme = darkColorScheme(
    primary = FrostPrimary,
    onPrimary = Color.Black,
    primaryContainer = FrostSecondary,
    onPrimaryContainer = FrostAccent,
    secondary = Color(0xFF38BDF8),
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF0C4A6E),
    onSecondaryContainer = Color(0xFFBAE6FD),
    tertiary = Color(0xFF7DD3FC),
    onTertiary = Color.Black,
    background = FrostBackground,
    onBackground = TextPrimary,
    surface = FrostSurface,
    onSurface = TextPrimary,
    surfaceVariant = FrostSurfaceVariant,
    onSurfaceVariant = TextSecondary,
    outline = FrostBorder,
    outlineVariant = FrostPrimary.copy(alpha = 0.4f)
)

private val ToxicViperColorScheme = darkColorScheme(
    primary = ToxicPrimary,
    onPrimary = Color.Black,
    primaryContainer = ToxicSecondary,
    onPrimaryContainer = ToxicAccent,
    secondary = Color(0xFF4ADE80),
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF14532D),
    onSecondaryContainer = Color(0xFFBBF7D0),
    tertiary = Color(0xFFA3E635),
    onTertiary = Color.Black,
    background = ToxicBackground,
    onBackground = TextPrimary,
    surface = ToxicSurface,
    onSurface = TextPrimary,
    surfaceVariant = ToxicSurfaceVariant,
    onSurfaceVariant = TextSecondary,
    outline = ToxicBorder,
    outlineVariant = ToxicPrimary.copy(alpha = 0.4f)
)

@Composable
fun MyApplicationTheme(
    appThemeMode: AppThemeMode = AppThemeMode.NEON_INFERNO,
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme: ColorScheme = when (appThemeMode) {
        AppThemeMode.NEON_INFERNO -> NeonInfernoColorScheme
        AppThemeMode.CYBERPUNK_NEON -> CyberpunkColorScheme
        AppThemeMode.ROYAL_AMETHYST -> RoyalAmethystColorScheme
        AppThemeMode.OBSIDIAN_RED -> ObsidianGhostColorScheme
        AppThemeMode.FROST_ARCTIC -> FrostArcticColorScheme
        AppThemeMode.TOXIC_VIPER -> ToxicViperColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
