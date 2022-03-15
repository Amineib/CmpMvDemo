package com.naar.nmovies.presentation.screens.moviedetails.util

import androidx.annotation.FloatRange
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.isFinite
import kotlin.math.pow

fun Modifier.iconButtonBackgroundScrim(
    enabled: Boolean = true,
    @FloatRange(from = 0.0, to = 1.0) alpha: Float = 0.4f,
    shape: Shape = CircleShape,
): Modifier = composed {
    if (enabled) {
        Modifier.background(
            color = MaterialTheme.colors.surface.copy(alpha = alpha),
            shape = shape,
        )
    } else this
}


fun Modifier.drawForegroundGradientScrim(
    color: Color,
    decay: Float = 3.0f,
    numStops: Int = 16,
    @FloatRange(from = 0.0, to = 1.0) startY: Float = 0f,
    @FloatRange(from = 0.0, to = 1.0) endY: Float = 1f,
): Modifier = composed {
    val colors = remember(color, numStops) {
        val baseAlpha = color.alpha
        List(numStops) { i ->
            val x = i * 1f / (numStops - 1)
            val opacity = x.pow(decay)
            color.copy(alpha = baseAlpha * opacity)
        }
    }

    drawWithContent {
        drawContent()
        drawRect(
            topLeft = Offset(x = 0f, y = startY * size.height),
            size = size.copy(height = (endY - startY) * size.height),
            brush = Brush.verticalGradient(colors = colors)
        )
    }
}


fun Modifier.bodyWidth() = fillMaxWidth()
    .wrapContentWidth(align = Alignment.CenterHorizontally)
    .composed {
        val bodyMaxWidth = Layout.bodyMaxWidth
        if (bodyMaxWidth.isFinite) widthIn(max = bodyMaxWidth) else this
    }


