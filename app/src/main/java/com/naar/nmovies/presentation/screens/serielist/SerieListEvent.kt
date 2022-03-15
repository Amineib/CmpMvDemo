package com.naar.nmovies.presentation.screens.serielist

sealed class SerieListEvent {
    data class OnItemClicked(val serieId : Int): SerieListEvent()
}