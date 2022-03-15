package com.naar.nmovies.domain.usecase.series

import androidx.paging.PagingData
import com.naar.nmovies.domain.models.SerieDetail
import com.naar.nmovies.domain.models.SerieListItem
import com.naar.nmovies.domain.repository.SerieRepository
import com.naar.nmovies.utils.Resource
import kotlinx.coroutines.flow.Flow


class GetSerieUseCase constructor(private val repository: SerieRepository) {
    fun getSeriesResultStream(query: String = ""): Flow<PagingData<SerieListItem>> {
        return repository.getPopularSeriesStream(query)
    }

    suspend fun getSerieDetails(serieId: Int) : Resource<SerieDetail> {
        return repository.getSerieDetail(serieId = serieId)
    }
}