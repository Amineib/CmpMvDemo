package com.naar.nmovies.presentation.screens.uimodels

data class MovieItemUi(
    val id: Int = 0,
    val name: String = "",
    val posterPath: String? = "",
    val popularity: Double? = 0.0,
    val synopsys: String? = "",
    val backDropPath: String? = ""
)
