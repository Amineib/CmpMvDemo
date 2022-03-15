package com.naar.nmovies.domain.models

import android.util.Log
import com.naar.nmovies.presentation.screens.uimodels.MovieDetailUi
import com.naar.nmovies.presentation.screens.uimodels.VideoUi
import com.naar.nmovies.utils.Constants

data class MovieDetail (
    val adult: Boolean?,
    val backdrop_path: String?,
    val belongs_to_collection: BelongsToCollection?,
    val budget: Int?,
    val genres: List<Genre>?,
    val homepage: String?,
    val id: Int?,
    val imdb_id: String?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val production_companies: List<ProductionCompany>?,
    val production_countries: List<ProductionCountry>?,
    val release_date: String?,
    val revenue: Int?,
    val runtime: Int?,
    val spoken_languages: List<SpokenLanguage>?,
    val status: String?,
    val tagline: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?,
    val videos: Videos?,
    val credits: Credits
    )

fun MovieDetail.toUi() : MovieDetailUi {

    var mVideos : MutableList<VideoUi> = mutableListOf()
    var videoLink = Constants.BASE_YOUTUBE_URL
    try {
        videos!!.results.filter {
            it.site.lowercase().contains("youtube")
        }.forEach{
            mVideos.add(it.toUi())
        }
    }catch (e: Exception){
        Log.e("Video Loading", "Video Loading error {${e.message}}")
        mVideos.add(VideoUi())
    }
    return MovieDetailUi(
        adult,
        Constants.BASE_IMG_URL + backdrop_path,
        belongs_to_collection,
        budget,
        genres,
        homepage,
        id,
        imdb_id,
        original_language,
        original_title,
        overview,
        popularity,
        Constants.BASE_IMG_URL+ poster_path,
        production_companies,
        production_countries,
        release_date,
        revenue,
        runtime,
        spoken_languages,
        status,
        tagline,
        title,
        video,
        vote_average,
        vote_count,
        mVideos,
        credits,
        videoLink = videoLink + if(mVideos != null && mVideos.size > 0)mVideos.get(0).key else {""}
    )
}