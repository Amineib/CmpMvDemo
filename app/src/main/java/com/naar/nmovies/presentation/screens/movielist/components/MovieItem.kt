package com.naar.nmovies.presentation.screens.movielist.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.naar.nmovies.presentation.screens.uimodels.MovieItemUi
import com.naar.nmovies.utils.Constants
import com.naar.nmovies.R

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MovieListItem(
    movie: MovieItemUi,
    modifier : Modifier = Modifier,
    onClick: (Int) -> Unit
) {
    Column(
        modifier = modifier.clickable { onClick(movie.id) }
    ) {
        val imgUrl = Constants.BASE_IMG_URL + movie.posterPath
        val painter = rememberImagePainter(data = imgUrl, builder = {
            crossfade(1000)
            error(R.drawable.ic_baseline_search_24)
        })
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .shadow(5.dp),
            contentScale = ContentScale.Crop
        )
    }
}


