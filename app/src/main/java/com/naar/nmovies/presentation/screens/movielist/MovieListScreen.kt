package com.naar.nmovies.presentation.screens.movielist

import android.view.View
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.kobakei.ratethisapp.RateThisApp
import com.naar.nmovies.presentation.screens.movielist.components.MovieListItem
import com.naar.nmovies.presentation.screens.serielist.items


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieListScreen(
    navController: NavController,
    viewModel: MovieListViewModel = hiltViewModel(),
    onMovieClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    Scaffold(

    ) {
        Surface(
           // color = MaterialTheme.colors.surface,
            modifier = modifier
        ) {
            AndroidView(factory = {
                View(
                    it,
                ).apply {
                    RateThisApp.onCreate(it)
                    //RateThisApp.init(RateThisApp.Config(0,4))
                    RateThisApp.showRateDialogIfNeeded(it)
                }
            }
            )
            var movies = viewModel.popularMovies.collectAsLazyPagingItems()
            val state = rememberLazyListState()
            LazyVerticalGrid(cells = GridCells.Fixed(2), contentPadding = PaddingValues(12.dp), state = state){
                items(movies){ movie ->
                    movie?.let {
                        MovieListItem(movie = it, onClick = {
                            onMovieClicked(it)
                        },
                            modifier = Modifier.height(300.dp).clip(RoundedCornerShape(20.dp)).padding(2.dp).shadow(2.dp)
                        )
                    }
                }
            }
        }
    }
}

