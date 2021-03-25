package com.example.movie.repository

import com.example.movie.data.entities.MovieList
import com.example.movie.data.remote.MovieDataSource

class MovieRepositoryImpl(private val dataSource: MovieDataSource) : MovieRepository {

    override suspend fun getUpComingMovies(): MovieList = dataSource.getUpComingMovies()
    override suspend fun getTopRatedMovies(): MovieList = dataSource.getTopRatedMovies()
    override suspend fun getPopularMovies(): MovieList = dataSource.getPopularMovies()
}