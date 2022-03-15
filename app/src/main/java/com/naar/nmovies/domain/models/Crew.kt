package com.naar.nmovies.domain.models

import com.naar.nmovies.presentation.screens.uimodels.CrewUi

data class Crew(
    val adult: Boolean?,
    val credit_id: String?,
    val department: String?,
    val gender: Gender?,
    val id: Int?,
    val job: String?,
    val known_for_department: String?,
    val name: String?,
    val original_name: String?,
    val popularity: Double?,
    val profile_path: String?
)


fun Crew.toUi() : CrewUi {
    return CrewUi(
        adult,
        credit_id,
        department,
        gender,
        id,
        job,
        known_for_department,
        name,
        original_name,
        popularity,
        profile_path
    )
}