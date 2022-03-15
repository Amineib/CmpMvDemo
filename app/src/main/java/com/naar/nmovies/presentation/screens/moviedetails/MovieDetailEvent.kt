package com.naar.nmovies.presentation.screens.moviedetails

import android.content.Context


sealed class MovieDetailEvent {
    object OnBackButtonClick: MovieDetailEvent()
    data class OnItemClicked(var movieId: Int) : MovieDetailEvent()
    data class OnPlayVideoButtonClicked(var context: Context) : MovieDetailEvent()
}