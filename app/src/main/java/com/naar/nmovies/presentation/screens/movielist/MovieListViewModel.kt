package com.naar.nmovies.presentation.screens.movielist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.naar.nmovies.domain.usecase.movie.GetMovieUseCase
import com.naar.nmovies.domain.models.toUi
import com.naar.nmovies.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val useCase: GetMovieUseCase,
) : ViewModel() {


    var popularMovies by mutableStateOf(
        useCase.getSearchMoviesResultStream()
        .map { it.map { movie -> movie.toUi() } }
        .cachedIn(viewModelScope)
    )

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
}

