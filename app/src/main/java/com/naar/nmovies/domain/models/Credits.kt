package com.naar.nmovies.domain.models

import com.naar.nmovies.presentation.screens.uimodels.CreditsUi

data class Credits(
    val cast: List<Cast> = emptyList(),
    val crew: List<Crew> = emptyList(),
    //val id: Int? = -1
)


fun Credits.toUi() : CreditsUi{
    return CreditsUi(
        //id = id,
        crew = crew?.mapNotNull { it.toUi() },
        cast = cast?.mapNotNull { it.toUi() }
    )
}