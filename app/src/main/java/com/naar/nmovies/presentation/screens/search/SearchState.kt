package com.naar.nmovies.presentation.screens.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.paging.PagingData
import com.naar.nmovies.domain.models.SerieListItem
import com.naar.nmovies.presentation.screens.uimodels.MovieItemUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class SearchState(
    var searchQuery : String = "",
    var isMovieChecked: Boolean = true,
    var isSerieChecked: Boolean = false,
    var movies : MutableState<Flow<PagingData<MovieItemUi>>> = mutableStateOf(emptyFlow<PagingData<MovieItemUi>>()),
    var series : MutableState<Flow<PagingData<SerieListItem>>> = mutableStateOf(emptyFlow<PagingData<SerieListItem>>())
)
