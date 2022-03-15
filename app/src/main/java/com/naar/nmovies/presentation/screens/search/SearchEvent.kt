package com.naar.nmovies.presentation.screens.search

import android.content.Context


sealed class SearchEvent {
    data class OnQueryChange(val query: String) : SearchEvent()
    data class OnSearch(val context: Context): SearchEvent()
    data class ShowInterstitial(val context: Context): SearchEvent()

}