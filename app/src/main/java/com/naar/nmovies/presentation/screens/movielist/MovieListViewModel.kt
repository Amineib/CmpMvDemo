package com.naar.nmovies.presentation.screens.movielist

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.paging.*
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxInterstitialAd
import com.naar.nmovies.domain.usecase.movie.GetMovieUseCase
import com.naar.nmovies.domain.models.toUi
import com.naar.nmovies.presentation.screens.moviedetails.MovieDetailEvent
import com.naar.nmovies.utils.Constants
import com.naar.nmovies.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val useCase: GetMovieUseCase,
) : ViewModel() , MaxAdListener {

    lateinit var context: Context

    private lateinit var interstitialAd: MaxInterstitialAd
    private var retryAttempt = 0.0
    val TAG = "Interstitial"

    var movieId: Int? = null


    var popularMovies by mutableStateOf(
        useCase.getSearchMoviesResultStream()
        .map { it.map { movie -> movie.toUi() } }
        .cachedIn(viewModelScope)
    )

    private val _uiEvent = Channel<MovieListEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun onEvent(event: MovieListEvent){
        when(event){
            is MovieListEvent.OnNavigate -> {
                /*if(interstitialAd.isReady){
                    movieId = event.movieId
                    //interstitialAd.showAd()
                }
                else{
                    movieId = event.movieId
                    notifyAdDone()
                }*/
                movieId = event.movieId
                notifyAdDone()
            }
        }
    }

    fun createInterstitial(){
        interstitialAd = MaxInterstitialAd(Constants.APPLOVIN_INTERSTITIAL, context as Activity?)
        interstitialAd.setListener(this)
        interstitialAd.loadAd()
    }


    override fun onAdLoaded(ad: MaxAd?) {
        Log.d(TAG, "Ad Loaded")
        retryAttempt = 0.0
    }

    override fun onAdDisplayed(ad: MaxAd?) {
        Log.d(TAG, "Ad Displayed")
    }

    override fun onAdHidden(ad: MaxAd?) {
        Log.d(TAG, "Ad Hidden")
        interstitialAd.loadAd()
        notifyAdDone()
    }

    override fun onAdClicked(ad: MaxAd?) {
        Log.d(TAG, "Ad Clicked")
    }

    override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {
        Log.d(TAG, "Ad Failed")
        retryAttempt++
        val delayMillis = TimeUnit.SECONDS.toMillis( Math.pow( 2.0, Math.min( 6.0, retryAttempt ) ).toLong() / 3)
        viewModelScope.launch {
            delay(delayMillis)
            interstitialAd.loadAd()
        }
    }

    override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {
        interstitialAd.loadAd()
        notifyAdDone()
    }

    fun notifyAdDone(){
        viewModelScope.launch {
            _uiEvent.send(MovieListEvent.OnNavigate(movieId!!))
        }
    }

}


