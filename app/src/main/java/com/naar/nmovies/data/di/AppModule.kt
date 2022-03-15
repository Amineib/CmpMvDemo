package com.naar.nmovies.data.di

import com.naar.nmovies.data.datasource.MoviePagingSource
import com.naar.nmovies.data.datasource.SeriePagingSource
import com.naar.nmovies.data.remote.MovieApi
import com.naar.nmovies.data.repository.MovieQuery
import com.naar.nmovies.data.repository.MovieRepositoryImpl
import com.naar.nmovies.data.repository.SerieQuery
import com.naar.nmovies.data.repository.SerieRepositoryImpl
import com.naar.nmovies.domain.usecase.movie.GetMovieUseCase
import com.naar.nmovies.domain.repository.MovieRepository
import com.naar.nmovies.domain.repository.SerieRepository
import com.naar.nmovies.domain.usecase.series.GetSerieUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMovieRepository(api: MovieApi): MovieRepository = MovieRepositoryImpl(api)

    @Singleton
    @Provides
    fun provideSerieRepository(api: MovieApi): SerieRepository = SerieRepositoryImpl(api)



    //TODO fix the parameters, avoid passing them with default values
    @Singleton
    @Provides
    fun provideMoviePagingSource(api: MovieApi): MoviePagingSource = MoviePagingSource(api,"",MovieQuery.PopularMovies)

    @Singleton
    @Provides
    fun provideSeriePagingSource(api: MovieApi) = SeriePagingSource(api = api, query = "", serieQuery = SerieQuery.TopSeries)

    @Singleton
    @Provides
    fun provideGetMovieUseCase(repository: MovieRepository): GetMovieUseCase {
        return GetMovieUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetSerieUseCase(repository: SerieRepository): GetSerieUseCase {
        return GetSerieUseCase(repository)
    }
}