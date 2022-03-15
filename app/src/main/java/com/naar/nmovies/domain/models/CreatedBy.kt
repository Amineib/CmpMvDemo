package com.naar.nmovies.domain.models

data class CreatedBy(
    val credit_id: String,
    val gender: Gender,
    val id: Int,
    val name: String,
    val profile_path: String?
)
