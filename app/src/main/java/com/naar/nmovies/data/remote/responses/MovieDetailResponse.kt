package com.naar.nmovies.data.remote.responses

import com.naar.nmovies.data.safeParse
import com.naar.nmovies.data.toDomain
import com.naar.nmovies.domain.models.MovieDetail
import com.naar.nmovies.domain.models.Videos

data class MovieDetailResponse(
    val adult: Boolean?,
    val backdrop_path: String?,
    val belongs_to_collection: BelongsToCollectionResponse?,
    val budget: Int?,
    val genres: List<GenreResponse>?,
    val homepage: String?,
    val id: Int?,
    val imdb_id: String?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val production_companies: List<ProductionCompanyResponse>?,
    val production_countries: List<ProductionCountryResponse>?,
    val release_date: String?,
    val revenue: Int?,
    val runtime: Int?,
    val spoken_languages: List<SpokenLanguageResponse>?,
    val status: String?,
    val tagline: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?,
    val videos: Videos?,
    val credits: CreditsResponse
)

fun MovieDetailResponse.toDomain() : MovieDetail? {
    return safeParse {
        MovieDetail(
            adult,
            backdrop_path,
            belongs_to_collection?.toDomain(),
            budget,
            genres?.mapNotNull { it.toDomain() },
            homepage,
            id,
            imdb_id,
            original_language,
            original_title,
            overview,
            popularity,
            poster_path,
            production_companies?.mapNotNull { it.toDomain() },
            production_countries?.mapNotNull { it.toDomain() },
            release_date,
            revenue,
            runtime,
            spoken_languages?.mapNotNull { it.toDomain() },
            status,
            tagline,
            title,
            video,
            vote_average,
            vote_count,
            videos,
            credits.toDomain()
        )
    }
}