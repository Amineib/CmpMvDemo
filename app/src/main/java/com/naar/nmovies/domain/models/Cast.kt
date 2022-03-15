package com.naar.nmovies.domain.models

import com.naar.nmovies.presentation.screens.uimodels.CastUi

data class Cast(
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

fun Cast.toUi() : CastUi{
    return CastUi(
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
        profile_path
    )
}