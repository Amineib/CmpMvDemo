package com.naar.nmovies.presentation.screens.moviedetails

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.naar.nmovies.R
import com.naar.nmovies.domain.models.Movie
import com.naar.nmovies.domain.usecase.movie.GetMovieUseCase
import com.naar.nmovies.domain.models.toUi
import com.naar.nmovies.utils.Constants
import com.naar.nmovies.utils.Resource
import com.naar.nmovies.utils.UiEvent
import com.naar.nmovies.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val useCase: GetMovieUseCase
) : ViewModel() {

    var state by mutableStateOf(MovieDetailState())
        private set

    lateinit var recommandations: Flow<PagingData<Movie>>

    lateinit var context: Context
    var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun onEvent(event: MovieDetailEvent){
        when(event){
            is MovieDetailEvent.OnBackButtonClick -> {
                viewModelScope.launch (Dispatchers.Main){
                    _uiEvent.send(UiEvent.NavigateUp)
                }
            }
            is MovieDetailEvent.OnItemClicked -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.NavigateToMovie(movieId = event.movieId))
                }
            }
            is MovieDetailEvent.OnPlayVideoButtonClicked -> {
                playMovieVideo()
            }
        }
    }

    fun loadMovie(movieId: Int){
        viewModelScope.launch {
            val result = useCase.getMovieDetail(movieId = movieId)
            when (result) {
                is Resource.Success -> {
                    val movieResponse = result.data!!
                    state = state.copy(
                        movieResponse.toUi()
                    )
                }
                is Resource.Error -> {
                    _uiEvent.send(UiEvent.ShowSnackBar(
                        UiText.DynamicString("No result found..")
                    ))
                }
            }
        }
    }

    fun loadMovieRecommandations(){
        viewModelScope.launch {
            val results = useCase.getRecommandatinons(state.movie.id!!)
            recommandations = results
        }
    }

    fun playMovieVideo(){
        var link = state.movie.videoLink
        if(link.isNotBlank() && link.isNotEmpty() && link != Constants.BASE_YOUTUBE_URL){
            try{
                var intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                context.startActivity(intent)
            }catch (e: Exception){
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.ShowSnackBar(UiText.DynamicString(context.getString(R.string.no_video_found))))
                }
            }
        } else {
            viewModelScope.launch {
                _uiEvent.send(UiEvent.ShowSnackBar(UiText.DynamicString(context.getString(R.string.no_video_found))))
            }
        }
    }



}






