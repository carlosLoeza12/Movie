package com.example.movie.domain

import com.example.movie.data.model.MovieList

interface MovieRepository {
    suspend fun getUpComingMovies(): MovieList
    suspend fun getTopRatedMovies(): MovieList
    suspend fun getPopularMovies(): MovieList
}