package com.naar.nmovies.presentation.screens.seriedetail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collect
import com.naar.nmovies.R
import com.naar.nmovies.presentation.screens.moviedetails.component.CrewProfile
import com.naar.nmovies.presentation.screens.moviedetails.component.DescriptionText
import com.naar.nmovies.presentation.screens.moviedetails.component.PlayButton
import com.naar.nmovies.presentation.screens.seriedetail.components.BackdropImage
import com.naar.nmovies.presentation.screens.seriedetail.components.GradientSpacer
import com.naar.nmovies.presentation.screens.seriedetail.components.SerieDetailsBar
import com.naar.nmovies.utils.UiEvent


@SuppressLint("ResourceType")
@Composable
fun SerieDetailScreen(
    modifier : Modifier = Modifier,
    navController: NavController,
    serieId: Int,
    viewModel: SerieDetailsViewModel = hiltViewModel()
) {
    Scaffold(

    ) {
        var context = LocalContext.current
        viewModel.context = context
        viewModel.loadSerieDetail(serieId)
        val serie = viewModel.state.serie
        val listState = rememberLazyListState()
        val scaffoldState = rememberScaffoldState()

        LaunchedEffect(true){
            viewModel.uiEvent.collect { event ->
                when(event){
                    is UiEvent.ShowSnackBar -> {
                        scaffoldState.snackbarHostState.showSnackbar(event.message.asString(context = context),duration = SnackbarDuration.Long)
                    }
                }
            }
        }


        LazyColumn(state = listState,
            modifier = modifier, contentPadding = it){
            item {
                BackdropImage(serie = serie ,
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



            //detail of serie
            item {
                SerieDetailsBar(
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth()
                        .padding(2.dp),
                    image = serie.poster_path,
                    title = serie.name,
                    note = serie.vote_average.toString(),
                    categories = serie.genres.mapNotNull { it.name }
                )
            }



            item {
                DescriptionText(text = serie.overview, modifier = Modifier.fillMaxWidth())
            }
            item {
                //TODO compute dominant color of backdrop image and gradient from it
                GradientSpacer(height = 12.dp)
            }

            item {
                PlayButton(modifier = Modifier.fillMaxWidth(), onClick = { viewModel.onEvent(SerieDetailEvent.OnPlayVideoButtonClicked(context)) }, text = stringResource(R.string.discover_serie_button))
            }

            item {
                GradientSpacer(height = 12.dp, inverse = false)
            }

            item {
                LazyRow(modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth(),
                ){
                    items(viewModel.state.serie.credits.cast){
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
        }
    }
}