package com.naar.nmovies.data.remote.responses

import com.naar.nmovies.domain.models.Genre

data class GenreResponse(
    val id: Int?,
    val name: String?
)

fun GenreResponse.toDomain() : Genre{
    return Genre(
        id,
        name
    )
}