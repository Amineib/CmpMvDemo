package com.naar.nmovies.presentation.screens.search

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxInterstitialAd
import com.naar.nmovies.domain.models.SerieListItem
import com.naar.nmovies.domain.models.toUi
import com.naar.nmovies.domain.usecase.movie.GetMovieUseCase
import com.naar.nmovies.domain.usecase.series.GetSerieUseCase
import com.naar.nmovies.presentation.screens.uimodels.MovieItemUi
import com.naar.nmovies.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import com.naar.nmovies.utils.UiEvent
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val movieUseCase: GetMovieUseCase,
    private val serieUseCase: GetSerieUseCase
    ) : ViewModel(), MaxAdListener {

    var searchState by mutableStateOf(SearchState())
    var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val TAG = "Interstitial"
    lateinit var context: Context
    private lateinit var interstitialAd: MaxInterstitialAd
    private var retryAttempt = 0.0
    init {
        executeSearch()
    }
    fun onEvent(event: SearchEvent){
        when(event){
            is SearchEvent.OnSearch -> {
                /*if(interstitialAd.isReady){
                    //interstitialAd.showAd()
                    executeSearch()
                }
                else{
                    executeSearch()
                }*/
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
        executeSearch()
    }

    override fun onAdClicked(ad: MaxAd?) {
        Log.d(TAG, "Ad Clicked")
    }

    override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {
        Log.d(TAG, "Ad Failed " +error?.message.toString())
        retryAttempt++
        val delayMillis = TimeUnit.SECONDS.toMillis( Math.pow( 2.0, Math.min( 6.0, retryAttempt ) ).toLong() / 3)
        viewModelScope.launch {
            delay(delayMillis)
            interstitialAd.loadAd()
        }
    }

    override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {
        interstitialAd.loadAd()
        executeSearch()
    }
}
