package com.naar.nmovies.presentation.screens.moviedetails

import com.naar.nmovies.presentation.screens.uimodels.MovieDetailUi

data class MovieDetailState (
    val movie: MovieDetailUi = MovieDetailUi(),
    //var recommandations: Flow<PagingData<Movie>> = emptyFlow()
        )