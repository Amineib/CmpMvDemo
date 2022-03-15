package com.naar.nmovies.presentation.screens.serielist

import androidx.lifecycle.ViewModel
import androidx.paging.map
import com.naar.nmovies.domain.usecase.series.GetSerieUseCase
import com.naar.nmovies.domain.models.SerieListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.naar.nmovies.presentation.screens.uimodels.SerieResultItemUi
import com.naar.nmovies.utils.Constants
import com.naar.nmovies.utils.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


@HiltViewModel
class SerieListViewModel @Inject constructor(serieUseCase: GetSerieUseCase) : ViewModel() {

    val series = serieUseCase.getSeriesResultStream().flatten {
        it.toUi()
    }.cachedIn(viewModelScope)

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SerieListEvent){
        when(event){
            is SerieListEvent.OnItemClicked -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.NavigateToSerie(event.serieId))
                }
            }
        }
    }


    /**
     * it allows to flatmap the PagingData<T>
     */
    inline fun <T : Any, R : Any> Flow<PagingData<T>>.flatten(crossinline transform: suspend (value: T) -> R) =
        this.map { pagingData -> pagingData.map { item -> transform(item) } }

    private fun SerieListItem.toUi(): SerieResultItemUi {
        return SerieResultItemUi(
            id = id,
            name = name,
            imageUrl = Constants.BASE_IMG_URL + poster_path
        )
    }
}