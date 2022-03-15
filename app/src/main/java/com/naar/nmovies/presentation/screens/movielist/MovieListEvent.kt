package com.naar.nmovies.presentation.screens.movielist

sealed class MovieListEvent {

    data class OnQueryChange(val query: String) : MovieListEvent()
    object OnSearch: MovieListEvent()

}