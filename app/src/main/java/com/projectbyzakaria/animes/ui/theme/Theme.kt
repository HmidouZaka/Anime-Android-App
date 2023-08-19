package com.projectbyzakaria.animes.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = blue,
    secondary = pink,
    background = black,
    surface = gray,
    onBackground = white,
    onSurface = red,
)

private val LightColorPalette = lightColors(
    primary = blue,
    secondary = pink,
    background = white,
    surface = gray,
    onBackground = black,
    onSurface = red,

)

@Composable
fun AnimesTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}