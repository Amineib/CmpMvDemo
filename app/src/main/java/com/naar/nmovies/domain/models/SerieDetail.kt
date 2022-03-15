package com.naar.nmovies.domain.models

import com.naar.nmovies.presentation.screens.uimodels.SerieDetailUi
import com.naar.nmovies.utils.Constants

data class SerieDetail(
    val backdrop_path: String,
    val first_air_date: String,
    val genres: List<Genre>,
    val id: Int,
    val name: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val seasons: List<Season>,
    val status: String,
    val vote_average: Double,
    val vote_count: Int,
    val videos: Videos,
    val credits: Credits
    )


fun SerieDetail.toUi() : SerieDetailUi {
    return SerieDetailUi(
        backdrop_path = backdrop_path,
        id = id,
        name = name,
        overview = overview,
        popularity = popularity,
        videos = videos,
        credits = credits,
        first_air_date = first_air_date,
        genres = genres,
        vote_average = vote_average,
        poster_path = poster_path,
        videoLink = Constants.BASE_YOUTUBE_URL + if(videos.results != null && videos.results.size > 0) videos.results.get(0).key else {""}
    )
}