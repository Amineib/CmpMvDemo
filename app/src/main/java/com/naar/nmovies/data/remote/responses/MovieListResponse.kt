package com.naar.nmovies.data.remote.responses

data class MovieListResponse(
    val page: Int,
    val results: List<MovieResponse>,
    val total_pages: Int,
    val total_results: Int
)