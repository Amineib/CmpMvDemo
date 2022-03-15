package com.naar.nmovies.data.remote.responses

import com.naar.nmovies.data.safeParse
import com.naar.nmovies.data.toGenderDomain
import com.naar.nmovies.domain.models.Crew
import com.naar.nmovies.utils.Constants

data class CrewResponse(
    val adult: Boolean?,
    val credit_id: String?,
    val department: String?,
    val gender: Int?,
    val id: Int?,
    val job: String?,
    val known_for_department: String?,
    val name: String?,
    val original_name: String?,
    val popularity: Double?,
    val profile_path: String?
)

fun CrewResponse.toDomain(): Crew?{
    return safeParse {
        Crew(
            adult,
            credit_id,
            department,
            gender.toGenderDomain(),
            id,
            job,
            known_for_department,
            name, original_name, popularity, Constants.BASE_IMG_URL+profile_path
        )
    }
}