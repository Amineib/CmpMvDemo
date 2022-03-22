package com.naar.nmovies.presentation.screens.serielist

import android.app.Activity
import android.content.Context
import android.util.Log
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
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxInterstitialAd
import com.naar.nmovies.presentation.screens.movielist.MovieListEvent
import com.naar.nmovies.presentation.screens.uimodels.SerieResultItemUi
import com.naar.nmovies.utils.Constants
import com.naar.nmovies.utils.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


@HiltViewModel
class SerieListViewModel @Inject constructor(serieUseCase: GetSerieUseCase) : ViewModel()  ,
    MaxAdListener {

    lateinit var context: Context

    private lateinit var interstitialAd: MaxInterstitialAd
    private var retryAttempt = 0.0
    val TAG = "Interstitial"

    var serieId: Int? = null

    val series = serieUseCase.getSeriesResultStream().flatten {
        it.toUi()
    }.cachedIn(viewModelScope)

    private val _uiEvent = Channel<SerieListEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SerieListEvent){
        when(event){
            is SerieListEvent.OnItemClicked -> {
                if(interstitialAd.isReady){
                    serieId = event.serieId
                    interstitialAd.showAd()
                }
                else{
                    serieId = event.serieId
                    notifyAdDone()
                }
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
            _uiEvent.send(SerieListEvent.OnItemClicked(serieId!!))
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