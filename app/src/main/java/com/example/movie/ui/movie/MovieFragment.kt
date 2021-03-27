package com.example.movie.ui.movie

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.example.movie.R
import com.example.movie.core.Resource
import com.example.movie.data.local.AppDatabase
import com.example.movie.data.local.MovieLocalDataSource
import com.example.movie.data.model.Movie
import com.example.movie.data.remote.MovieRemoteDataSource
import com.example.movie.databinding.FragmentMovieBinding
import com.example.movie.presentation.MovieViewModel
import com.example.movie.presentation.MovieViewModelFactory
import com.example.movie.domain.MovieRepositoryImpl
import com.example.movie.domain.RetrofitClient
import com.example.movie.ui.movie.adapters.concat.MovieAdapter
import com.example.movie.ui.movie.adapters.concat.PopularConcatAdapter
import com.example.movie.ui.movie.adapters.concat.TopRatedConcatAdapter
import com.example.movie.ui.movie.adapters.concat.UpComingConcatAdapter


class MovieFragment : Fragment(R.layout.fragment_movie), MovieAdapter.onMovieClickListener {

    private lateinit var binding: FragmentMovieBinding
    private lateinit var conCatAdapter: ConcatAdapter
    // se le asigna un delegado para  que haga la creacion de la instancia del viewModel sea sencilla
    //viewModels viene de la libreria ktx de viewModel
    private val viewModel by viewModels<MovieViewModel> {
        MovieViewModelFactory(
            MovieRepositoryImpl(
                MovieRemoteDataSource(RetrofitClient.webservice),
                    MovieLocalDataSource(AppDatabase.getDatabase(requireContext()).movieDao())
            )
        )
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)
        conCatAdapter = ConcatAdapter()
        //se retorna el live data,necesitamos observarlo para ver cuando retorna un valor
        viewModel.fetchMainScreenMovies().observe(viewLifecycleOwner, Observer {result->
            when(result){
                is Resource.Loading -> {
                    Log.e("liveData", "Loading...")
                    binding.relativeProgressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.relativeProgressBar.visibility = View.GONE
                    Log.e("liveData", "upComing: ${result.data.first}")
                    conCatAdapter.apply {
                        addAdapter(0,UpComingConcatAdapter(MovieAdapter(result.data.first.results,this@MovieFragment)))
                        addAdapter(1,TopRatedConcatAdapter(MovieAdapter(result.data.second.results,this@MovieFragment)))
                        addAdapter(2,PopularConcatAdapter(MovieAdapter(result.data.third.results,this@MovieFragment)))
                    }
                    binding.recyclerMovies.adapter= conCatAdapter
                }
                is Resource.Error -> {
                    binding.relativeProgressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Error: ${result.exception}", Toast.LENGTH_SHORT).show()
                    Log.e("liveData", "${result.exception}")
                }
            }
        })
    }

    override fun onMovieClick(movie: Movie) {
     val action = MovieFragmentDirections.actionMovieFragmentToDetailMovieFragment(movie.poster_path,
             movie.backdrop_path,
             movie.vote_average.toFloat(),
             movie.vote_count,
             movie.overview,
             movie.title,
             movie.original_language,
             movie.release_date
     )
        findNavController().navigate(action)
    }
}