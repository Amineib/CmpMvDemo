package com.naar.nmovies.data.remote

import com.naar.nmovies.data.remote.responses.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    //Popular movies
    @GET("movie/popular")
    suspend fun getMoviesList(
        @Query("page") page: Int
    ): MovieListResponse

    //Top rated Movies
    @GET("movie/top_rated")
    suspend fun getTopRatedMoviesList(
        @Query("page") page: Int
    ): MovieListResponse

    //Upcoming Movies
    @GET("movie/upcoming")
    suspend fun getUpcomingMoviesList(
        @Query("page") page: Int
    ): MovieListResponse

    //Now Airing
    @GET("movie/now_playing")
    suspend fun getNowAiring(
        @Query("page") page: Int
    ): MovieListResponse

    //Search for a movie
    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("page") page: Int
    ) : MovieListResponse

    //Get recommandations based on a movie`
    @GET("movie/{movieId}/recommendations")
    suspend fun getMovieRecommandations(
        @Path("movieId") movieId: Int,
        @Query("page") page: Int):MovieListResponse



    //TODO fix literal string
    @GET("movie/{movieId}?append_to_response=videos,credits")
    suspend fun getMovieDetail(@Path("movieId") movieId: Int): MovieDetailResponse

    @GET("movie/{movieId}/videos")
    suspend fun getVideos(@Path("movieId") movieId: Int): VideosResponse

    //Get movie credits
    @GET("movie/{movieId}/credits")
    suspend fun getMovieCredits(@Path("movieId") movieId: Int): CreditsResponse

    /*
    Series related methods
    */
    @GET("tv/popular")
    suspend fun getPopularSeriesList(@Query("page") page: Int): SerieListResponse

    @GET("tv/{serieId}?append_to_response=videos,credits")
    suspend fun getSerieDetail(@Path("serieId") serieId: Int): SerieDetailResponse


    //Search for a serie
    @GET("search/tv")
    suspend fun searchSerie(
        @Query("query") query: String,
        @Query("page") page: Int
    ) : SerieListResponse

    /*
    Series videos (trailer and related videos)
    https://api.themoviedb.org/3/tv/93405/videos?api_key=ec76b30fad5adfe026ae2c000a2e15b8&language=us-US
     */
    @GET("tv/{serieId}/videos")
    suspend fun getSerieVideos(@Path("serieId") serieId : Int) : VideosResponse


}