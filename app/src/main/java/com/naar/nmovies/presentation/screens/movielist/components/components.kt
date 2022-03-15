package putlocker.solarmovie.movies123movies.gomovies.flix.presentation.screens.movielist.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.naar.nmovies.presentation.screens.movielist.components.MovieItemVertical
import com.naar.nmovies.presentation.screens.movielist.components.MovieListItem
import com.naar.nmovies.presentation.screens.uimodels.MovieItemUi
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MoviesList(
    movies: Flow<PagingData<MovieItemUi>>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onMovieClicked: (Int) -> Unit
) {
    val lazyMovieItems: LazyPagingItems<MovieItemUi> = movies.collectAsLazyPagingItems()
    val listState = rememberLazyListState()

    LazyRow(
        modifier = modifier,
        state = listState,
        contentPadding = PaddingValues(4.dp)
    ){
        items(lazyMovieItems){ item ->
            item?.let {
                MovieListItem(
                    movie = item,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(140.dp)
                        .padding(4.dp)
                ) { movieId ->
                    //TODO : get rid of lateral string
                    onMovieClicked(movieId)
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MoviesListHighlight(
    movies: Flow<PagingData<MovieItemUi>>,
    modifier: Modifier = Modifier,
    onMovieClicked: (Int) -> Unit
) {
    val lazyMovieItems: LazyPagingItems<MovieItemUi> = movies.collectAsLazyPagingItems()
    val listState = rememberLazyListState()

    LazyRow(
        modifier = modifier
            .height(260.dp),
        state = listState,
        contentPadding = PaddingValues(0.dp)
    ){
        items(lazyMovieItems){ item ->
            //Text(text = "Item ${item?.name}")
            item?.let {
                MovieItemVertical(
                    movie = item,
                    //modifier = Modifier.height(300.dp)
                ) { movieId ->
                    //TODO : get rid of lateral string
                    //navController.navigate(Route.MOVIES_DETAIL+"/${movieId}")
                    onMovieClicked(movieId)
                }
            }
        }
    }
}

