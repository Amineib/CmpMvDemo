package com.naar.nmovies.ads

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.applovin.mediation.ads.MaxAdView
import com.naar.nmovies.utils.Constants


@Composable
fun AdBanner(
    modifier : Modifier = Modifier,
    viewModel: AdBannerViewModel = hiltViewModel()
) {
    var context = LocalContext.current
    viewModel.setAdView(context = context)
    val adView = viewModel.maxAdView
    Column (
        modifier = modifier,
        horizontalAlignment =  Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        AndroidView(factory = {
            adView!!
        },
            modifier = Modifier.fillMaxSize()
        )
    }
}
