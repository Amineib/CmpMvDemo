package com.naar.nmovies.presentation.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.naar.nmovies.domain.models.SerieListItem
import com.naar.nmovies.domain.models.toUi
import com.naar.nmovies.domain.usecase.movie.GetMovieUseCase
import com.naar.nmovies.domain.usecase.series.GetSerieUseCase
import com.naar.nmovies.presentation.screens.uimodels.MovieItemUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import com.naar.nmovies.utils.UiEvent
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val movieUseCase: GetMovieUseCase,
    private val serieUseCase: GetSerieUseCase
    ) : ViewModel(){

    var searchState by mutableStateOf(SearchState())
    var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    init {
        executeSearch()
    }
    fun onEvent(event: SearchEvent){
        when(event){
            is SearchEvent.OnSearch -> {
                executeSearch()
            }
            is SearchEvent.OnQueryChange -> {
                searchState = searchState.copy(searchQuery = event.query)
            }
        }
    }

    private fun executeSearch(){
        viewModelScope.launch {
            if(searchState.isMovieChecked){
                searchState.movies.value = movieUseCase.getSearchMoviesResultStream(query = searchState.searchQuery).mapNotNull {
                    it.map {
                        it.toUi()
                    }
                }.cachedIn(viewModelScope)
                searchState.series.value = emptyFlow<PagingData<SerieListItem>>()
            }else{
                searchState.series.value = serieUseCase.getSeriesResultStream(query = searchState.searchQuery).cachedIn(viewModelScope)
                searchState.movies.value = emptyFlow<PagingData<MovieItemUi>>()
            }
        }
    }
}
