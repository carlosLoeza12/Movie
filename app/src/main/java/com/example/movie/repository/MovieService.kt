package com.example.movie.repository

import com.example.movie.data.entities.MovieList
import com.example.movie.utils.MovieConstants
import com.example.movie.utils.MovieConstants.BASE_URL
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.converter.gson.GsonConverterFactory


interface MovieService {
    @GET("movie/upcoming")
    suspend fun getUpComingMovies(@Query("api_key") apiKey: String,@Query("language")language: String="es-mx"): MovieList

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("api_key") apiKey: String,@Query("language")language: String="es-mx"): MovieList

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String,@Query("language")language: String="es-mx"): MovieList
}

object RetrofitClient{
    val webservice by lazy {
        Retrofit.Builder()
                .baseUrl(MovieConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build().create(MovieService::class.java)
    }
}

