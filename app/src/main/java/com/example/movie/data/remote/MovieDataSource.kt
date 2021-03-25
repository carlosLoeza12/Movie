package com.example.movie.data.remote

import com.example.movie.data.entities.MovieList
import com.example.movie.repository.MovieService
import com.example.movie.utils.MovieConstants

class MovieDataSource(private val movieService: MovieService) {
    suspend fun getUpComingMovies(): MovieList = movieService.getUpComingMovies(MovieConstants.API_KEY)
    suspend fun getTopRatedMovies(): MovieList = movieService.getTopRatedMovies(MovieConstants.API_KEY)
    suspend fun getPopularMovies(): MovieList = movieService.getPopularMovies(MovieConstants.API_KEY)

}