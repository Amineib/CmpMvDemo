package com.naar.nmovies.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.naar.nmovies.data.datasource.MoviePagingSource
import com.naar.nmovies.data.remote.MovieApi
import com.naar.nmovies.data.remote.responses.toDomain
import com.naar.nmovies.domain.models.*
import com.naar.nmovies.domain.repository.MovieRepository
import com.naar.nmovies.utils.Resource
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@ActivityScoped
class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi
) : MovieRepository {

    /**
     * Get details of a specific movie
     */
    override suspend fun getMovieDetail(movieId: Int): Resource<MovieDetail> {
        return try {
            val response = api.getMovieDetail(movieId)

            val movie = response.toDomain()
            //Log.d("Credits", movie?.credits?.crew?.size.toString())
            if (movie != null) {
                Resource.Success(movie)
            } else {
                Resource.Error("Movie Not Found")
            }
        } catch (e: Exception) {
            Resource.Error("an unknown error has occured" + e.message.toString())
        }
    }


    /**
     * Get videos of a specific movie
     */
    override suspend fun getMovieVideos(movieId: Int): Resource<Videos> {
        val response  = try {
            api.getVideos(movieId)
        }
        catch (e: Exception) {
            return Resource.Error("an unknown error has occured" + e.message.toString())
        }
        //Log.d("MovieVideosRepo", response.results?.size.toString()+ " " + response.id)
        return with(response){
            Resource.Success(
                this.toDomain()
            )
        }
    }

    override suspend fun getMovieCredits(movieId: Int): Resource<Credits> {
        val response = try {
            api.getMovieCredits(movieId = movieId)
        }
        catch(e: java.lang.Exception){
            return Resource.Error("an unknown error has occured"+ e.message.toString())
        }
        //TODO fix the defautl Credits constructor
        return Resource.Success(response.toDomain())
    }

    /**
     * Search popularmovies exposed as a stream of data that will emit
     * every time we get more data from the network.
     */
    override fun getSearchMoviesResultStream(query: String): Flow<PagingData<Movie>> {
        var queryType = if(query.isNotEmpty() && query.isNotBlank()) {
            MovieQuery.Search(query)
        }else{
            MovieQuery.PopularMovies
        }
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingSource(api = api, query = query,queryType) }
        ).flow
    }

    override fun getTopRatedMoviesResultStream(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingSource(api = api, query = "", MovieQuery.TopMovies) }
        ).flow
    }

    override fun getNowAiring(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingSource(api = api, query = "", MovieQuery.NowAiring) }
        ).flow
    }

    override fun getUpcoming(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingSource(api = api, query = "", MovieQuery.Upcoming) }
        ).flow
    }


    override fun getMovieRecommandations(movieId: Int): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingSource(api = api, query = "", MovieQuery.Recommandations(movieId = movieId)) }
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 1000
    }
}

sealed class MovieQuery {
    data class Search(val query: String) : MovieQuery()
    object TopMovies: MovieQuery()
    object PopularMovies: MovieQuery()
    object NowAiring: MovieQuery()
    object Upcoming: MovieQuery()
    data class Recommandations(val movieId: Int) : MovieQuery()
}