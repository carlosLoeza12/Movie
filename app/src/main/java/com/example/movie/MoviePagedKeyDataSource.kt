package com.example.movie

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.movie.data.local.MovieDao
import com.example.movie.data.model.Movie
import com.example.movie.domain.MovieRepository
import com.example.movie.domain.MovieService
import com.example.movie.utils.MovieConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class MoviePagedKeyDataSource @Inject constructor(private val movieService: MovieService)  : PageKeyedDataSource<Int, Movie>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {

        //coorrutina
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    movieService.getPopularMovies(MovieConstants.API_KEY,"",1)
                }
                val movies = response.results
                Log.e("response", "response $movies")
                movies?.let {
                    callback.onResult(movies, null, 2)
                }

            } catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        TODO("Not yet implemented")
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        //coorrutina
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    movieService.getPopularMovies(MovieConstants.API_KEY,"",params.key)
                }
                val movies = response.results
                //Log.e("response", "response ${response.body()}")
                movies?.let {
                    callback.onResult(movies, params.key + 1)
                }
            } catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }
    }
}