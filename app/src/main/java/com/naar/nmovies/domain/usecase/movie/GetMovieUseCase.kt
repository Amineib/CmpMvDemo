package com.naar.nmovies.domain.usecase.movie

import androidx.paging.PagingData
import com.naar.nmovies.domain.models.Credits
import com.naar.nmovies.domain.models.Movie
import com.naar.nmovies.domain.models.MovieDetail
import com.naar.nmovies.domain.models.Videos
import com.naar.nmovies.domain.repository.MovieRepository
import com.naar.nmovies.utils.Resource
import kotlinx.coroutines.flow.Flow

class GetMovieUseCase constructor(private val repository: MovieRepository) {

    suspend fun getMovieDetail(movieId: Int): Resource<MovieDetail> {
         var result =  repository.getMovieDetail(movieId)
        return result
    }

    suspend fun getMovieVideos(movieId: Int): Resource<Videos> {
        return repository.getMovieVideos(movieId)
    }

    suspend fun getMovieCredits(movieId: Int): Resource<Credits> {
        return repository.getMovieCredits(movieId)
    }

    fun getSearchMoviesResultStream(query: String = ""): Flow<PagingData<Movie>>{
        return repository.getSearchMoviesResultStream(query)
    }

    fun getTopRatedMovies(): Flow<PagingData<Movie>>{
        return repository.getTopRatedMoviesResultStream()
    }

    fun getNowAiring(): Flow<PagingData<Movie>>{
        return repository.getNowAiring()
    }

    fun getUpcoming(): Flow<PagingData<Movie>>{
        return repository.getNowAiring()
    }

    fun getRecommandatinons(movieId: Int): Flow<PagingData<Movie>> {
        return repository.getMovieRecommandations(movieId)
    }

}