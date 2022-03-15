package com.naar.nmovies.presentation.screens.movielist

data class MovieSearchState (
    val searchQuery : String = "",
    val isHintVisible: Boolean = false,
    val isSearching: Boolean = false,
        )