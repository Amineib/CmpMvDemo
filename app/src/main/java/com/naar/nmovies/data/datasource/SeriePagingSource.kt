package com.naar.nmovies.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.naar.nmovies.data.remote.MovieApi
import com.naar.nmovies.data.remote.responses.SerieListResponse
import com.naar.nmovies.data.repository.SerieQuery
import com.naar.nmovies.data.requiredField
import com.naar.nmovies.data.safeParse
import com.naar.nmovies.domain.models.SerieListItem
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SeriePagingSource @Inject constructor(
    private val query: String,
    private val api: MovieApi,
    private val serieQuery: SerieQuery
) : PagingSource<Int, SerieListItem>() {

    override fun getRefreshKey(state: PagingState<Int, SerieListItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SerieListItem> {
        return try {
            val nextPage = params.key ?: 1
            val serieList : SerieListResponse
            when(serieQuery){
                is SerieQuery.TopSeries -> {
                    serieList =  api.getPopularSeriesList(nextPage)
                }
                is SerieQuery.Search -> {
                    serieList =  api.searchSerie(query = query, nextPage)
                }
            }
            val data = serieList.results.mapNotNull { result ->
                result.safeParse {
                    SerieListItem(
                        id = requiredField("id", result.id),
                        first_air_date = result.first_air_date,
                        backdrop_path = result.backdrop_path,
                        name = requiredField("name", result.name),
                        genre_ids = result.genre_ids,
                        original_language = result.original_language,
                        original_name = result.original_name,
                        origin_country = result.origin_country,
                        overview = result.overview,
                        popularity = result.popularity,
                        poster_path = result.poster_path,
                        vote_average = result.vote_average,
                        vote_count = result.vote_count
                    )
                }
            }

            LoadResult.Page(
                data = data,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (serieList.results.isEmpty()) null else serieList.page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }


}