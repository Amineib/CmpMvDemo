package com.naar.nmovies.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.naar.nmovies.data.datasource.SeriePagingSource
import com.naar.nmovies.data.remote.MovieApi
import com.naar.nmovies.data.remote.responses.toDomain
import com.naar.nmovies.domain.models.*
import com.naar.nmovies.domain.repository.SerieRepository
import com.naar.nmovies.utils.Resource
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ActivityScoped
class SerieRepositoryImpl @Inject constructor(
    private val api: MovieApi
) : SerieRepository {

    override fun getPopularSeriesStream(query : String): Flow<PagingData<SerieListItem>> {
        var queryType = if(query.isNotEmpty() && query.isNotBlank()){
            SerieQuery.Search(query)
        }else {
            SerieQuery.TopSeries
        }
        Log.d("QueryType", queryType.javaClass.toString())
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SeriePagingSource(api = api, query = query, serieQuery = queryType) }
        ).flow
    }

    override suspend fun getSerieDetail(serieId: Int): Resource<SerieDetail> {
        val response = try {
            api.getSerieDetail(serieId)
        } catch (e: Exception) {
            return Resource.Error("an unknown error has occured" + e.message.toString())
        }
        return with(response) {
            Resource.Success(
                this.toDomain()
            )
        }
    }


    override suspend fun getSerieVideos(serieId: Int): Resource<Videos> {
        val response  = try {
            api.getSerieVideos(serieId)
        }
        catch (e: Exception) {
            return Resource.Error("an unknown error has occured" + e.message.toString())
        }
        //TODO Verify why VideoResponse.toDomain() is not working
        return with(response){
            Resource.Success(
                this.toDomain()
            )
        }
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 1000
    }
}


sealed class SerieQuery {
    data class Search(val query: String) : SerieQuery()
    object TopSeries: SerieQuery()
}