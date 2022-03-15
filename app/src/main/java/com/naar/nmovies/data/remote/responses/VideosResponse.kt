package com.naar.nmovies.data.remote.responses

import com.naar.nmovies.domain.models.Videos

data class VideosResponse(
    val id: Int?,
    val results: List<VideoResponse>?
)

fun VideosResponse.toDomain(): Videos {
    return Videos(
        id?: -1,
        results?.map { it.toDomain() } ?: emptyList()
    )
}