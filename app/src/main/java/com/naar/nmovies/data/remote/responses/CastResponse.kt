package com.naar.nmovies.data.remote.responses

import com.naar.nmovies.data.safeParse
import com.naar.nmovies.domain.models.Cast
import com.naar.nmovies.utils.Constants

data class CastResponse(
    val adult: Boolean?,
    val character: String?,
    val credit_id: String?,
    val gender: Int?,
    val id: Int?,
    val known_for_department: String?,
    val name: String?,
    val order: Int?,
    val original_name: String?,
    val popularity: Double?,
    val profile_path: String?
)

fun CastResponse.toDomain() : Cast? {
    return safeParse {
        Cast(
            adult,
            character,
            credit_id,
            gender,
            id,
            known_for_department,
            name,
            order,
            original_name,
            popularity,
            Constants.BASE_IMG_URL + profile_path
        )
    }
}