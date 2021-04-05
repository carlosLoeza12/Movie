package com.example.movie.domain

import com.example.movie.core.InternetCheck
import com.example.movie.data.local.MovieLocalDataSource
import com.example.movie.data.model.MovieList
import com.example.movie.data.model.toMovieEntity
import com.example.movie.data.remote.MovieRemoteDataSource
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieLocalDataSource
) : MovieRepository {

    override suspend fun getUpComingMovies(): MovieList {
        return if (InternetCheck.isNetworkAvailable()) {
            //remoteDataSource.getUpComingMovies() devuelve MovieList
            remoteDataSource.getUpComingMovies().results.forEach { movie ->
                localDataSource.saveMovie(movie.toMovieEntity("upcoming"))
            }
            localDataSource.getUpComingMovies()
        } else {
            return localDataSource.getUpComingMovies()
        }
    }

    override suspend fun getTopRatedMovies(): MovieList {
        return if (InternetCheck.isNetworkAvailable()) {
            remoteDataSource.getTopRatedMovies().results.forEach { movie ->
                localDataSource.saveMovie(movie.toMovieEntity("toprated"))
            }
            localDataSource.getTopRatedMovies()
        } else {
            localDataSource.getTopRatedMovies()
        }
    }

    override suspend fun getPopularMovies(): MovieList {
        return if (InternetCheck.isNetworkAvailable()) {
            remoteDataSource.getPopularMovies().results.forEach { movie ->
                localDataSource.saveMovie(movie.toMovieEntity("popular"))
            }
            localDataSource.getPopularMovies()
        } else {
            localDataSource.getPopularMovies()
        }
    }
}