package com.naar.nmovies.data.remote.responses

data class SerieListResponse(
    val page: Int,
    val results: List<SerieListItemResponse>,
    val total_pages: Int,
    val total_results: Int
)