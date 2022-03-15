package com.naar.nmovies.domain.repository

import androidx.paging.PagingData
import com.naar.nmovies.domain.models.SerieListItem
import com.naar.nmovies.domain.models.SerieDetail
import com.naar.nmovies.domain.models.Videos
import com.naar.nmovies.utils.Resource
import kotlinx.coroutines.flow.Flow

interface SerieRepository {
    fun getPopularSeriesStream(query: String) : Flow<PagingData<SerieListItem>>
    suspend fun getSerieDetail(serieId: Int) : Resource<SerieDetail>
    suspend fun getSerieVideos(serieId: Int) : Resource<Videos>
}