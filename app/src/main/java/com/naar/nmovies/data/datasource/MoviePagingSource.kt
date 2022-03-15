package com.naar.nmovies.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.naar.nmovies.data.remote.MovieApi
import com.naar.nmovies.data.remote.responses.MovieListResponse
import com.naar.nmovies.data.repository.MovieQuery
import com.naar.nmovies.data.requiredField
import com.naar.nmovies.data.safeParse
import com.naar.nmovies.domain.models.Movie
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


/*
     Paging source for both queried and popular movies
 */
class MoviePagingSource @Inject constructor(
    private val api: MovieApi,
    private val query: String,
    private val movieQuery: MovieQuery
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPage = params.key ?: 1
            val moviesList : MovieListResponse

            when(movieQuery){
                is MovieQuery.Search -> {
                    moviesList  = api.searchMovie(query = query, nextPage)
                }
                is MovieQuery.PopularMovies -> {
                    moviesList  = api.getMoviesList(nextPage)

                }
                is MovieQuery.TopMovies -> {
                    moviesList  = api.getTopRatedMoviesList(nextPage)
                }
                is MovieQuery.NowAiring -> {
                    moviesList  = api.getNowAiring(nextPage)
                }
                is MovieQuery.Upcoming -> {
                    moviesList  = api.getUpcomingMoviesList(nextPage)
                }
                is MovieQuery.Recommandations -> {
                    moviesList = api.getMovieRecommandations(movieId = movieQuery.movieId, page = nextPage)
                }
            }

            val data = moviesList.results.mapNotNull { result ->
               safeParse {
                   Movie(
                       id = requiredField("id",result.id) ,
                       name = requiredField("name",result.title) ,
                       posterPath = result.poster_path,
                       popularity = result.popularity,
                       synopsys = result.overview,
                       backdropPath = result.backdrop_path
                   )
               }
            }

            LoadResult.Page(
                data = data,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (moviesList.results.isEmpty()) null else moviesList.page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}