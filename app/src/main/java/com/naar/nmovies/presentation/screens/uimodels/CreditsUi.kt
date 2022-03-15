package com.naar.nmovies.presentation.screens.uimodels

data class CreditsUi(
    val cast: List<CastUi>? = mutableListOf(),
    val crew: List<CrewUi>? = mutableListOf(),
    val id: Int? = 0
)
