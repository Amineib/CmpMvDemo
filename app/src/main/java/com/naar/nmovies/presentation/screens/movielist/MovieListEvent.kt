package com.naar.nmovies.presentation.screens.movielist

import androidx.navigation.NavController

sealed class MovieListEvent {

    data class OnQueryChange(val query: String) : MovieListEvent()
    object OnSearch: MovieListEvent()
    data class OnNavigate(var movieId: Int): MovieListEvent()

}