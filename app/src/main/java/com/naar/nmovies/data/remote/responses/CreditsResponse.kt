package com.naar.nmovies.data.remote.responses

import com.naar.nmovies.domain.models.Credits

data class CreditsResponse(
    val cast: List<CastResponse>,
    val crew: List<CrewResponse>,
    //val id: Int?
)

fun CreditsResponse.toDomain() : Credits {
    return Credits(
        //id = id,
        cast = cast.mapNotNull { it.toDomain() },
        crew = crew.mapNotNull { it.toDomain() }
    )
}