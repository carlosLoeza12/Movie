package com.example.movie.ui.movie

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.example.movie.R
import com.example.movie.core.Resource
import com.example.movie.data.local.MovieDao
import com.example.movie.data.model.Movie
import com.example.movie.databinding.FragmentMovieBinding
import com.example.movie.domain.MovieService
import com.example.movie.presentation.MovieViewModel
import com.example.movie.ui.movie.adapters.concat.MovieAdapter
import com.example.movie.ui.movie.adapters.concat.PopularConcatAdapter
import com.example.movie.ui.movie.adapters.concat.TopRatedConcatAdapter
import com.example.movie.ui.movie.adapters.concat.UpComingConcatAdapter
import com.example.movie.utils.sharedPreferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieFragment : Fragment(R.layout.fragment_movie), MovieAdapter.onMovieClickListener {

    private lateinit var binding: FragmentMovieBinding
    private lateinit var conCatAdapter: ConcatAdapter
    private var upComingPos: Int = 0
    private var topRatedgPos: Int = 0
    private var popularPos: Int = 0
    private lateinit var shared: sharedPreferences
    //probando

    @Inject
    lateinit var movieDao: MovieDao

    private val viewModel by viewModels<MovieViewModel> ()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)

        initElements()

    }

    private fun initElements() {

        shared = sharedPreferences(requireContext())
        getMoviesState()

        binding.swipeRefreshMovie.setOnRefreshListener {
            resetPosition()
            shared.deletePreferences()
            binding.swipeRefreshMovie.isRefreshing = false
            getMoviesState()
        }

    }

    private fun getMoviesState() {

        if (!shared.getState()) {
            getMovies()
            shared.saveState(true)
        } else {

            viewModel.data.value?.let {
                conCatAdapter = ConcatAdapter()
                resetPosition()

                when (shared.getTypeMovie()) {
                    "upcoming" -> upComingPos = shared.getPosition()
                    "toprated" -> topRatedgPos = shared.getPosition()
                    "popular" -> popularPos = shared.getPosition()
                }

                conCatAdapter.apply {
                    addAdapter(UpComingConcatAdapter(MovieAdapter(it.first.results, this@MovieFragment), upComingPos))
                    addAdapter(TopRatedConcatAdapter(MovieAdapter(it.second.results, this@MovieFragment), topRatedgPos))
                    addAdapter(PopularConcatAdapter(MovieAdapter(it.third.results, this@MovieFragment), popularPos))
                }
                binding.recyclerMovies.adapter = conCatAdapter

            } ?: run {
                shared.saveState(false)
                resetPosition()
                shared.deletePreferences()
                getMoviesState()
            }
        }
    }

    private fun getMovies() {
        //se retorna el live data,necesitamos observarlo para ver cuando retorna un valor
        conCatAdapter = ConcatAdapter()
        viewModel.fetchMainScreenMovies().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    Log.e("liveData", "Loading...")
                    binding.relativeProgressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.relativeProgressBar.visibility = View.GONE
                    Log.e("liveData", "upComing: ${result.data.first}")
                    conCatAdapter.apply {
                        addAdapter(UpComingConcatAdapter(MovieAdapter(result.data.first.results, this@MovieFragment), upComingPos))
                        addAdapter(TopRatedConcatAdapter(MovieAdapter(result.data.second.results, this@MovieFragment), topRatedgPos))
                        addAdapter(PopularConcatAdapter(MovieAdapter(result.data.third.results, this@MovieFragment), popularPos))

                        viewModel.data.value = result.data
                    }
                    binding.recyclerMovies.adapter = conCatAdapter
                }
                is Resource.Error -> {
                    binding.relativeProgressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Error: ${result.exception}", Toast.LENGTH_SHORT).show()
                    Log.e("liveData", "${result.exception}")
                }
            }
        })
    }

    override fun onMovieClick(movie: Movie, position: Int) {

        val action = MovieFragmentDirections.actionMovieFragmentToDetailMovieFragment(
                movie.poster_path,
                movie.backdrop_path,
                movie.vote_average.toFloat(),
                movie.vote_count,
                movie.overview,
                movie.title,
                movie.original_language,
                movie.release_date,
        )
        findNavController().navigate(action)
        shared.savePosition(position)
        shared.saveTypeMovie(movie.movie_type)
    }

    private fun resetPosition() {
        upComingPos = 0
        topRatedgPos = 0
        popularPos = 0
    }

    override fun onDestroy() {
        super.onDestroy()
        shared.deletePreferences()
    }

}