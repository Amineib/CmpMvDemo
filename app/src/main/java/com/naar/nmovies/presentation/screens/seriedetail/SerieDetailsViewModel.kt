package com.naar.nmovies.presentation.screens.seriedetail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naar.nmovies.R
import com.naar.nmovies.domain.models.toUi
import com.naar.nmovies.domain.usecase.series.GetSerieUseCase
import com.naar.nmovies.utils.Constants
import com.naar.nmovies.utils.Resource
import com.naar.nmovies.utils.UiEvent
import com.naar.nmovies.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class SerieDetailsViewModel @Inject constructor(
    private val useCase: GetSerieUseCase
) : ViewModel() {

    var state by mutableStateOf(SerieDetailState())
    lateinit var context: Context
    var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()



    fun onEvent(event: SerieDetailEvent){
        when(event){
            is SerieDetailEvent.OnPlayVideoButtonClicked -> {
                playMovieVideo()
            }
        }
    }

    fun loadSerieDetail(serieId: Int) {
        viewModelScope.launch {
            val result = useCase.getSerieDetails(serieId = serieId)
            when (result) {
                is Resource.Success -> {
                    val serieDetail = result.data!!
                    state = state.copy(serie = serieDetail.toUi())
                }
                is Resource.Error -> {
                    //error handling
                }
            }
        }
    }


    fun playMovieVideo(){
        var link = state.serie.videoLink
        if(link.isNotBlank() && link.isNotEmpty() && link != Constants.BASE_YOUTUBE_URL){
            try{
                var intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                context.startActivity(intent)
            }catch (e: Exception){
                viewModelScope.launch {
                    Log.d("Videos", "Error Video")
                    _uiEvent.send(UiEvent.ShowSnackBar(UiText.DynamicString(context.getString(R.string.no_video_found))))
                }
            }
        } else {
            viewModelScope.launch {
                Log.d("Videos", "Error Video")
                _uiEvent.send(UiEvent.ShowSnackBar(UiText.DynamicString(context.getString(R.string.no_video_found))))
            }
        }
    }
}


