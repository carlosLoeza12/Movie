package com.example.movie.presentation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.movie.core.Resource
import com.example.movie.repository.MovieRepository
import kotlinx.coroutines.Dispatchers

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

   /* fun fetchUpComingMovies() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Succes(movieRepository.getUpComingMovies()))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }*/

    fun fetchMainScreenMovies()= liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Succes(Triple(movieRepository.getUpComingMovies(), movieRepository.getTopRatedMovies(), movieRepository.getPopularMovies())))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

}

class MovieViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MovieRepository::class.java).newInstance(repository)
    }
}