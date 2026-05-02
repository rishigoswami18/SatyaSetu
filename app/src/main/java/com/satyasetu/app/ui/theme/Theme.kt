package com.satyasetu.app.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = SaffronPrimary,
    onPrimary = TextWhite,
    primaryContainer = SaffronDark,
    secondary = LightBlue,
    onSecondary = TextWhite,
    secondaryContainer = DeepBlue,
    tertiary = AccentGreen,
    onTertiary = TextWhite,
    background = DarkBackground,
    onBackground = TextWhite,
    surface = DarkSurface,
    onSurface = TextWhite,
    surfaceVariant = DarkCard,
    onSurfaceVariant = TextLight,
    error = StatusRejected,
    onError = TextWhite
)

private val LightColorScheme = lightColorScheme(
    primary = SaffronPrimary,
    onPrimary = TextWhite,
    primaryContainer = SaffronLight,
    secondary = MediumBlue,
    onSecondary = TextWhite,
    secondaryContainer = LightBlue,
    tertiary = AccentGreen,
    onTertiary = TextWhite,
    background = LightBackground,
    onBackground = TextDark,
    surface = LightSurface,
    onSurface = TextDark,
    surfaceVariant = LightCard,
    onSurfaceVariant = TextMuted,
    error = StatusRejected,
    onError = TextWhite
)

@Composable
fun SatyaSetuTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = SatyaSetuTypography,
        content = content
    )
}
