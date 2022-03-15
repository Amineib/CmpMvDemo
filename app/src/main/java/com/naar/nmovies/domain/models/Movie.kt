package com.naar.nmovies.domain.models

import com.naar.nmovies.presentation.screens.uimodels.MovieItemUi

data class Movie(
    val id: Int = 0,
    val name: String = "",
    val posterPath: String? = "",
    val popularity: Double? = 0.0,
    val synopsys: String? = "",
    val backdropPath: String? = "",
)


fun Movie.toUi(): MovieItemUi {
    return MovieItemUi(id, name, posterPath, popularity,synopsys,backdropPath)
}