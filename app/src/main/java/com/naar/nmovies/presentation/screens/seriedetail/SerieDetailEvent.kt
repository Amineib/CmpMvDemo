package com.naar.nmovies.presentation.screens.seriedetail

import android.content.Context

sealed class SerieDetailEvent{
    object OnBackButtonClick: SerieDetailEvent()
    data class OnItemClicked(var serieId: Int) : SerieDetailEvent()
    data class OnPlayVideoButtonClicked(var context: Context) : SerieDetailEvent()
}