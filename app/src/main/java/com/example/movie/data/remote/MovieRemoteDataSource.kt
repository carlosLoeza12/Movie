package com.example.movie.data.remote

import androidx.paging.PageKeyedDataSource
import com.example.movie.data.model.Movie
import com.example.movie.data.model.MovieList
import com.example.movie.domain.MovieService
import com.example.movie.utils.MovieConstants
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(private val movieService: MovieService) {

    suspend fun getUpComingMovies(): MovieList =
        movieService.getUpComingMovies(MovieConstants.API_KEY,"",1)

    suspend fun getTopRatedMovies(): MovieList =
        movieService.getTopRatedMovies(MovieConstants.API_KEY,"",1)

    suspend fun getPopularMovies(): MovieList =
        movieService.getPopularMovies(MovieConstants.API_KEY,"",1)


}