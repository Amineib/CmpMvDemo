package com.naar.nmovies.data.remote.responses

data class EpisodeResponse(
    val air_date: String?,
    val crew: List<CrewResponse>?,
    val episode_number: Int?,
    val guest_stars: List<GuestStarResponse>?,
    val id: Int?,
    val name: String?,
    val overview: String?,
    val production_code: String?,
    val season_number: Int?,
    val still_path: String?,
    val vote_average: Double?,
    val vote_count: Int?
)