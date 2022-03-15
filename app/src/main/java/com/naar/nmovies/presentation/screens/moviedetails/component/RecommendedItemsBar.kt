package com.naar.nmovies.presentation.screens.moviedetails.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import com.naar.nmovies.domain.models.Movie
import com.naar.nmovies.utils.Constants
import kotlinx.coroutines.flow.Flow

@Composable
fun RecommendedItems(
    modifier : Modifier = Modifier,
    moviesList: Flow<PagingData<Movie>>,
    itemWidth: Dp,
    itemHeight: Dp,
    onItemClick: (Int) -> Unit
) {
    val movies = moviesList.collectAsLazyPagingItems()
    val listState = rememberLazyListState()
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        state = listState,
        contentPadding = PaddingValues(4.dp)
    ){
        items(movies){ movie ->
            movie?.let {
                MovieItem(movie = it, onClick = { onItemClick(it) }, itemWidth = itemWidth, itemHeight = itemHeight)
            }
        }
    }
}


@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    movie: Movie = Movie(),
    onClick: (Int) -> Unit,
    itemWidth: Dp,
    itemHeight: Dp
) {
    Column (
        modifier = modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(12.dp))
            .width(itemWidth),
            //.height(itemHeight),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
            ){
        Surface(
            elevation = 8.dp
        ) {
            val imgUrl = Constants.BASE_IMG_URL + movie.posterPath
            val painter = rememberImagePainter(data = imgUrl, builder = {
                crossfade(1000)
            })
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .height(itemHeight)
                    .width(itemWidth)
                    .clickable { onClick(movie.id) },
                contentScale = ContentScale.Crop,
            )
        }
        Text(text = movie.name,
        style = MaterialTheme.typography.subtitle2,
        textAlign = TextAlign.Center,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        modifier = Modifier.padding(horizontal = 5.dp),
            color = MaterialTheme.colors.onPrimary
        )
    }
}