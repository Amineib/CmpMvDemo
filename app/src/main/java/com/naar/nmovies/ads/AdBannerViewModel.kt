package com.naar.nmovies.ads

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdViewAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxAdView
import com.naar.nmovies.utils.Constants


class AdBannerViewModel :  ViewModel(), MaxAdViewAdListener {


    val TAG = "AdBannerViewModel"
    var maxAdView : MaxAdView? = null

    fun setAdView(context: Context){
        if(maxAdView == null){
            maxAdView = MaxAdView(
                Constants.APPLOVIN_BANNER,
                context
            )
            maxAdView?.loadAd()
        }
    }


    override fun onAdLoaded(ad: MaxAd?) {
        Log.d(TAG, "adLoaded ")
    }

    override fun onAdDisplayed(ad: MaxAd?) {
        Log.d(TAG, "adDisplayed ")
    }

    override fun onAdHidden(ad: MaxAd?) {
        Log.d(TAG, "adHidden ")
    }

    override fun onAdClicked(ad: MaxAd?) {
        Log.d(TAG, "adClicked ")
    }

    override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {
        Log.d(TAG, "adFailed "+ error?.message.toString())
    }

    override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {
        Log.d(TAG, "onAdDisplayFailed "+ error?.message.toString())
    }

    override fun onAdExpanded(ad: MaxAd?) {
        TODO("Not yet implemented")
    }

    override fun onAdCollapsed(ad: MaxAd?) {
        TODO("Not yet implemented")
    }
}