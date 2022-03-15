package com.naar.nmovies.presentation.screens.uimodels

import com.naar.nmovies.domain.models.Gender

data class CrewUi(
    val adult: Boolean? = false,
    val credit_id: String? = "",
    val department: String? ="",
    val gender: Gender? = Gender.UNKNOWN,
    val id: Int? = 0,
    val job: String? = "",
    val known_for_department: String? = "",
    val name: String? = "",
    val original_name: String? = "",
    val popularity: Double? = .0,
    val profile_path: String? = ""
)
