package com.naar.nmovies.domain.models

import com.naar.nmovies.data.remote.responses.SeasonResponse

data class Season(
    val air_date: String?,
    val episode_count: Int?,
    val id: Int?,
    val name: String?,
    val overview: String?,
    val poster_path: String?,
    val season_number: Int?
)


fun SeasonResponse.toDomain(): Season {
    return Season(
        air_date = air_date,
        episode_count = episode_count,
        id = id,
        name = name,
        overview = overview,
        poster_path = poster_path,
        season_number = season_number,
    )
}