package com.naar.nmovies.presentation.screens.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.naar.nmovies.R
import com.naar.nmovies.navigation.Route
import com.naar.nmovies.presentation.components.SearchBar
import com.naar.nmovies.presentation.screens.movielist.components.MovieListItem
import com.naar.nmovies.presentation.screens.uimodels.SerieResultItemUi
import com.naar.nmovies.presentation.screens.serielist.components.SerieListItem
import com.naar.nmovies.presentation.screens.serielist.items
import com.naar.nmovies.utils.Constants

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchScreen(
    navController: NavController,
    modifier : Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state = viewModel.searchState
    var movies = state.movies.value.collectAsLazyPagingItems()
    var series = state.series.value.collectAsLazyPagingItems()
    val context = LocalContext.current

    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
            ){
        SearchBar(
            hint = stringResource(R.string.search_hint),
            text = state.searchQuery,
            onValueChange = {viewModel.onEvent(SearchEvent.OnQueryChange(it))},
            onSearch = {
                viewModel.onEvent(SearchEvent.OnSearch(context))
            }
        )


        //true movies ok  false series ok
        var radioState by remember {
            mutableStateOf(viewModel.searchState.isMovieChecked)
        }
        Column(
            Modifier
                .selectableGroup()
                .fillMaxWidth()
                .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
            Row {
                Text(text = "Movies")
                RadioButton(selected = radioState, onClick = {
                    radioState = true
                    viewModel.searchState.isMovieChecked = true
                })
            }
            Row {
                Text(text = "Series")
                RadioButton(selected = !radioState, onClick = {
                    radioState = false
                    viewModel.searchState.isMovieChecked = false
                })
            }
        }
        
        Column(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)) {
            Button(
                onClick = {
                    viewModel.onEvent(SearchEvent.OnSearch(context = context))
                },
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = stringResource(id = R.string.search_hint), modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            }
        }


        var listState = rememberLazyListState()
        LazyVerticalGrid(state = listState, cells = GridCells.Fixed(2), modifier = Modifier
            .fillMaxSize()){
            if (state.isMovieChecked){
                items(movies){ movie ->
                    movie?.let {
                        MovieListItem(movie = it,
                            onClick = { navController.navigate(Route.MOVIES_DETAIL + "/" + movie.id) },
                            modifier = Modifier
                                .height(300.dp)
                                .fillMaxWidth()
                                .padding(4.dp))
                    }
                }
            }else{
                items(series){ serie ->
                    serie?.let {
                        SerieListItem(serie = SerieResultItemUi(it.id,it.name,
                            Constants.BASE_IMG_URL+ it.poster_path.toString()),
                            modifier = Modifier
                                .height(300.dp)
                                .fillMaxWidth()
                                .padding(4.dp),
                        onItemClicked = {
                            navController.navigate(Route.SERIES_DETAIL + "/" + serie.id)
                        })
                    }
                }
            }
        }
    }

}

