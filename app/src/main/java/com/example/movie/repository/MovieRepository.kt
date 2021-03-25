package com.example.movie.repository

import com.example.movie.data.entities.MovieList

interface MovieRepository {
    suspend fun getUpComingMovies() : MovieList
    suspend fun getTopRatedMovies() : MovieList
    suspend fun getPopularMovies() : MovieList
}