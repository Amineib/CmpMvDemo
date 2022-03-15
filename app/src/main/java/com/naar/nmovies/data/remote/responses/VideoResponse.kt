package com.naar.nmovies.data.remote.responses

import com.naar.nmovies.domain.models.Video

data class VideoResponse(
    val id: String?,
    val key: String?,
    val name: String?,
    val official: Boolean?,
    val site: String?
)

fun VideoResponse.toDomain() : Video {
    return Video (
            id ?: "",
        key ?: "",
        name ?: "",
        official ?: false,
        site ?: ""
            )
}