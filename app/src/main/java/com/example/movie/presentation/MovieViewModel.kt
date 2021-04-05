package com.example.movie.presentation
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.movie.core.Resource
import com.example.movie.data.model.MovieList
import com.example.movie.domain.MovieRepository
import kotlinx.coroutines.Dispatchers

class MovieViewModel @ViewModelInject constructor (private val movieRepository: MovieRepository) : ViewModel() {

    val data = MutableLiveData<Triple<MovieList, MovieList, MovieList>>()
    fun fetchMainScreenMovies()= liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(Triple(movieRepository.getUpComingMovies(),
                    movieRepository.getTopRatedMovies(),
                    movieRepository.getPopularMovies())))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }
    init {
        Log.e("liveData", "init")
    }

}




