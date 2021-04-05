package com.example.movie.domain

import com.example.movie.data.model.MovieList
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("movie/upcoming")
    suspend fun getUpComingMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "es-mx"
    ): MovieList

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "es-mx"
    ): MovieList

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "es-mx"
    ): MovieList
}


