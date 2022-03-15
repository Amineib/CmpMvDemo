package com.naar.nmovies.presentation.screens.uimodels

import com.naar.nmovies.domain.models.Credits
import com.naar.nmovies.domain.models.Genre
import com.naar.nmovies.domain.models.Videos

data class SerieDetailUi(
    val backdrop_path: String = "",
    val id: Int = 0,
    val name: String = "",
    val genres: List<Genre> = mutableListOf(),
    val overview: String = "",
    val popularity: Double = 0.0,
    val videos : Videos = Videos(id = -1, emptyList()),
    val credits: Credits = Credits(emptyList(), emptyList()),
    val first_air_date: String = "",
    val vote_average : Double = 0.0,
    val poster_path :String = "",
    val videoLink: String = ""
    )

