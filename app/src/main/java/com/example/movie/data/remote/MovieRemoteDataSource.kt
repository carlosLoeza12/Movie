package com.example.movie.data.remote

import com.example.movie.data.model.MovieList
import com.example.movie.domain.MovieService
import com.example.movie.utils.MovieConstants

class MovieRemoteDataSource(private val movieService: MovieService) {

    suspend fun getUpComingMovies(): MovieList =
        movieService.getUpComingMovies(MovieConstants.API_KEY)

    suspend fun getTopRatedMovies(): MovieList =
        movieService.getTopRatedMovies(MovieConstants.API_KEY)

    suspend fun getPopularMovies(): MovieList =
        movieService.getPopularMovies(MovieConstants.API_KEY)

}