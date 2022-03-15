package com.naar.nmovies.domain.repository

import androidx.paging.PagingData
import com.naar.nmovies.domain.models.Credits
import com.naar.nmovies.domain.models.Movie
import com.naar.nmovies.domain.models.MovieDetail
import com.naar.nmovies.domain.models.Videos
import com.naar.nmovies.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovieDetail(movieId: Int): Resource<MovieDetail>
    suspend fun getMovieVideos(movieId: Int): Resource<Videos>
    suspend fun getMovieCredits(movieId: Int): Resource<Credits>

    fun getMovieRecommandations(movieId: Int): Flow<PagingData<Movie>>


    fun getSearchMoviesResultStream(query: String): Flow<PagingData<Movie>>
    fun getTopRatedMoviesResultStream(): Flow<PagingData<Movie>>
    fun getNowAiring(): Flow<PagingData<Movie>>
    fun getUpcoming(): Flow<PagingData<Movie>>
}