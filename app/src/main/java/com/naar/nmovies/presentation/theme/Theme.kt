package com.naar.nmovies.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

/*private val LightColorPalette = lightColors(
    primary = BlackGrey,
    primaryVariant = LightGrey,
    secondary = Reddish,
    background = Color.Gray.copy(0.05f),
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = BlackGrey,
    onSurface = BlackGrey
    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)*/


private val LightColorPalette = lightColors(
    primary = RED800,
    primaryVariant = RED800,
    secondary = Red500,
    secondaryVariant = Red500,
    background = Black900,
    surface = Black900,
    error = YELLOW900,
    onPrimary = GREY100,
    onSecondary = GREY100,
    onBackground = GREY100,
    onSurface = GREY100,
    onError = YELLOW100
)


@Composable
fun BaseMoviesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
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