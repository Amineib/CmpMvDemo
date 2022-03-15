package com.naar.nmovies.data.remote.responses

import com.naar.nmovies.domain.models.*
import com.naar.nmovies.utils.Constants

data class SerieDetailResponse(
    val backdrop_path: String?,
    val first_air_date: String?,
    val genres: List<GenreResponse>?,
    val id: Int?,
    val name: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val seasons: List<SeasonResponse>?,
    val status: String?,
    val vote_average: Double?,
    val vote_count: Int?,
    val videos: VideosResponse?,
    val credits: CreditsResponse?
)

fun SerieDetailResponse.toDomain() : SerieDetail{
    return SerieDetail(
         Constants.BASE_IMG_URL+backdrop_path ?: "",
        first_air_date ?: "",
        genres?.map { it.toDomain() } ?: emptyList(),
        id ?: -1,
        name ?: "",
        overview ?: "",
        popularity?: .0,
        Constants.BASE_IMG_URL+poster_path ?: "",
        seasons?.map { it.toDomain() } ?: emptyList(),
        status ?: "",
        vote_average ?: .0,
        vote_count ?: 0,
        videos?.toDomain() ?: Videos(),
        credits?.toDomain() ?: Credits(emptyList(), emptyList())
    )
}