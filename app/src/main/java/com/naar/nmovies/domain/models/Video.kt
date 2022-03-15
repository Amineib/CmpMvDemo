package com.naar.nmovies.domain.models

import com.naar.nmovies.presentation.screens.uimodels.VideoUi

data class Video(
    val id: String,
    val key: String,
    val name: String,
    val official: Boolean,
    val site: String
)

fun Video.toUi() : VideoUi {
    return VideoUi(
            id, key, name, official, site
        )
}