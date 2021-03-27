package com.example.movie.data.local

import com.example.movie.data.model.MovieEntity
import com.example.movie.data.model.MovieList
import com.example.movie.data.model.toMovieList

class MovieLocalDataSource(private val movieDao: MovieDao) {

    suspend fun getUpComingMovies(): MovieList {
        return movieDao.getAllMovies().filter { it.movie_type == "upcoming" }.toMovieList()
    }

    suspend fun getTopRatedMovies(): MovieList {
        return movieDao.getAllMovies().filter { it.movie_type == "toprated" }.toMovieList()
    }

    suspend fun getPopularMovies(): MovieList {
        return movieDao.getAllMovies().filter { it.movie_type == "popular" }.toMovieList()
    }

    suspend fun saveMovie(movie: MovieEntity) {
        movieDao.saveMovie(movie)
    }
}