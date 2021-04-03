package com.example.movie.presentation
import android.util.Log
import androidx.lifecycle.*
import com.example.movie.core.Resource
import com.example.movie.data.model.MovieList
import com.example.movie.domain.MovieRepository
import com.example.movie.domain.MovieRepositoryImpl
import kotlinx.coroutines.Dispatchers

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

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

class MovieViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MovieRepository::class.java).newInstance(repository)
    }
}


