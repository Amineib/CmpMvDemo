package com.naar.nmovies.presentation.screens.moviedetails.component

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import coil.compose.rememberImagePainter
import com.naar.nmovies.domain.models.Movie
import com.naar.nmovies.presentation.screens.moviedetails.MovieDetailEvent
import com.naar.nmovies.presentation.screens.moviedetails.MovieDetailsViewModel
import com.naar.nmovies.presentation.screens.moviedetails.util.Layout
import com.naar.nmovies.presentation.screens.moviedetails.util.drawForegroundGradientScrim
import com.naar.nmovies.presentation.screens.uimodels.MovieDetailUi
import com.naar.nmovies.utils.UiEvent
import com.naar.nmovies.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect


@Composable
fun MovieDetailScreen(
    onNavigateUp: () -> Unit,
    onNavigateToMovie: (Int) -> Unit,
    scaffoldState: ScaffoldState,
    viewModel: MovieDetailsViewModel = hiltViewModel(),
    movieId: Int
) {
    val context = LocalContext.current
    viewModel.context = context
    viewModel.loadMovie(movieId = movieId)
    viewModel.loadMovieRecommandations()
    //viewModel.createInterstitial()
    val state = viewModel.state
    val movie = state.movie
    val recommendations = viewModel.recommandations

    LaunchedEffect(true){
        viewModel.uiEvent.collect { event ->
            when(event){
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message.asString(context = context),duration = SnackbarDuration.Long)
                }
                is UiEvent.NavigateUp -> {
                    onNavigateUp()
                }
                is UiEvent.NavigateToMovie -> {
                    onNavigateToMovie(event.movieId)
                }
            }
        }
    }


    val listState = rememberLazyListState()
    com.google.accompanist.insets.ui.Scaffold(
    ){ contentPadding ->
            ShowDetailScrollingContent(
                movie = movie,
                listState = listState,
                modifier = Modifier.fillMaxSize(), recommandations = recommendations,
                viewModel = viewModel, context = context
            )
    }
}



@SuppressLint("ResourceType")
@Composable
fun ShowDetailScrollingContent(
    listState: LazyListState,
    modifier : Modifier = Modifier,
    movie: MovieDetailUi,
    recommandations: Flow<PagingData<Movie>>,
    viewModel: MovieDetailsViewModel,
    context: Context
) {
    val gutter = Layout.gutter
    val bodyMargin = Layout.bodyMargin

    LazyColumn(state = listState,
    modifier = modifier){
        item {
            BackdropImage(movie = movie ,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 10)
                    .clipToBounds()
                    .offset {
                        IntOffset(
                            x = 0,
                            y = if (listState.firstVisibleItemIndex == 0) {
                                listState.firstVisibleItemScrollOffset / 2
                            } else 0
                        )
                    }
            )
        }


        item {
            Spacer(modifier = Modifier.height(max(gutter, bodyMargin)))
        }

        //Movie detail
        item {
            MovieDetailsBar(image = movie.poster_path!!,
                title = movie.title!!,
                note = movie.vote_average.toString(),
                synopsys = movie.overview!!,
                genres = movie.genres!!
            )
        }


        item {
            Spacer(modifier = Modifier.height(max(gutter, bodyMargin)))
        }

        item {
            PlayButton(modifier = Modifier.fillMaxWidth(),onClick = { viewModel.onEvent(MovieDetailEvent.OnPlayVideoButtonClicked(context)) }, text = stringResource(R.string.discover_movie_button))
        }


        item {
            Spacer(modifier = Modifier.height(max(gutter, bodyMargin)))
        }



        //cast
        item {
            LazyRow(modifier = Modifier
                .height(150.dp)
                .fillMaxWidth(),
            ){
                items(viewModel.state.movie.credits.cast){
                    Column(modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                        CrewProfile(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(100.dp),
                            imgData = it.profile_path ?: stringResource(id = R.drawable.ic_outline_error_outline_24),
                            it.name ?: ""
                        )
                    }
                }
            }
        }

        //Recommended movies
        item{
            HomeHeaderTitle(text = stringResource(R.string.you_might_also_like), modifier = Modifier.padding(horizontal = 16.dp))

        }
        item {
            RecommendedItems(moviesList = recommandations,
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth()
                    .shadow(1.dp),
                itemHeight = 190.dp,
                itemWidth = 100.dp,
                onItemClick = { viewModel.onEvent(MovieDetailEvent.OnItemClicked(it)) }
            )
        }
    }
}


@Composable
private fun BackdropImage(
    movie: MovieDetailUi,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Box {
            if (movie.backdrop_path != null) {
                Image(
                    //TODO move base url var to the domain layer
                    painter = rememberImagePainter(movie.backdrop_path) {
                        crossfade(true)
                    },
                    contentDescription = "content",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .drawForegroundGradientScrim(Color.Black.copy(alpha = 0.7f)),
                )
            }

            val originalTextStyle = MaterialTheme.typography.h4

            val shadowSize = with(LocalDensity.current) {
                originalTextStyle.fontSize.toPx() / 16
            }

            Text(
                text = movie.title ?: "",
                style = originalTextStyle.copy(
                    color = MaterialTheme.colors.onBackground,
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


