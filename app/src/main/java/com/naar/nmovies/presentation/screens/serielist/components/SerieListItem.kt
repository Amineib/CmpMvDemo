package com.naar.nmovies.presentation.screens.serielist.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.naar.nmovies.R
import com.naar.nmovies.presentation.screens.uimodels.SerieResultItemUi

@Composable
fun SerieListItem(
    modifier :Modifier = Modifier,
    serie: SerieResultItemUi,
    onItemClicked: (Int) -> Unit
) {
    Column(
        modifier = modifier.clickable { onItemClicked(serie.id) }
    ) {
        Image(painter = rememberImagePainter(
            data = serie?.imageUrl,
            builder = {
                error(R.drawable.ic_outline_hide_image_24)
                crossfade(true)
            }
        ), contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(12.dp)))
    }
}