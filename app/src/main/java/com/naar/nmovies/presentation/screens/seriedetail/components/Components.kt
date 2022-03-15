package com.naar.nmovies.presentation.screens.seriedetail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.naar.nmovies.presentation.screens.moviedetails.util.Layout
import com.naar.nmovies.presentation.screens.moviedetails.util.drawForegroundGradientScrim
import com.naar.nmovies.presentation.screens.uimodels.SerieDetailUi
import com.naar.nmovies.R



@Composable
fun BackdropImage(
    serie: SerieDetailUi,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Box {
            if (serie.backdrop_path != null) {
                Image(
                    //TODO move base url var to the domain layer
                    painter = rememberImagePainter(serie.backdrop_path) {
                        crossfade(true)
                        error(R.drawable.ic_baseline_search_24)
                    },
                    contentDescription = "content",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .drawForegroundGradientScrim(Color.Black.copy(alpha = 0.7f))
                )
            }

            val originalTextStyle = MaterialTheme.typography.h4

            val shadowSize = with(LocalDensity.current) {
                originalTextStyle.fontSize.toPx() / 16
            }

            Text(
                text = serie.name ?: "",
                style = originalTextStyle.copy(
                    color = Color.White,
                    shadow = Shadow(
                        color = Color.Black,
                        offset = Offset(shadowSize, shadowSize),
                        blurRadius = 0.1f,
                    )
                ),
                fontWeight = FontWeight.Thin,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(Layout.gutter * 2)
            )
        }
    }
}


@Composable
fun GradientSpacer(
    modifier: Modifier = Modifier,
    height: Dp = 12.dp,
    inverse: Boolean = false
) {
    var colors = mutableListOf<Color>()
    if(inverse){
        colors.add(MaterialTheme.colors.primary)
        colors.add(MaterialTheme.colors.background)
    }else{
        colors.add(MaterialTheme.colors.background)
        colors.add(MaterialTheme.colors.primary)
    }
    Spacer(modifier = modifier.height(height = height)
        .background(
            Brush.horizontalGradient(colors = colors),
            RectangleShape)
        .fillMaxWidth())
}