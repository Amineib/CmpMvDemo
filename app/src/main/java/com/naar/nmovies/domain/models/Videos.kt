package com.naar.nmovies.domain.models

import com.naar.nmovies.presentation.screens.uimodels.VideosUi


data class Videos (
    val id: Int = -1,
    val results: List<Video> = emptyList()
)


fun Videos.toUi() : VideosUi {
        return VideosUi(
            id,
            results?.mapNotNull { it.toUi() }
        )
}