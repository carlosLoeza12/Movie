package com.example.movie.ui.movie

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.example.movie.R
import com.example.movie.core.Resource
import com.example.movie.data.entities.Movie
import com.example.movie.data.remote.MovieDataSource
import com.example.movie.databinding.FragmentMovieBinding
import com.example.movie.presentation.MovieViewModel
import com.example.movie.presentation.MovieViewModelFactory
import com.example.movie.repository.MovieRepositoryImpl
import com.example.movie.repository.MovieService
import com.example.movie.repository.RetrofitClient
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
                MovieDataSource(RetrofitClient.webservice)
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
                is Resource.Succes -> {
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

/*viewModel.fetchUpComingMovies().observe(viewLifecycleOwner, Observer {result->
            when(result){
                is Resource.Loading -> {
                    Log.e("LiveData","Loading...")
                }
                is Resource.Succes -> {
                    Log.e("LiveData","${result.data}")
                }
                is Resource.Error -> {
                    Log.e("LiveData","${result.exception}")
                }
            }
        })*/
/*Nosotros creamos ese Factory con la interfaz del repositorio, no su implementación, pero nosotros
//
//debemos proveer la implementación del repositorio para que justamente este interfaz conozca de esa implementación
//
//y que después la podamos usar justamente acá, porque si no, cuando creamos una instancia no vas a
//
//ver en esta interfaz ÂCual implementación apuntar no a la hora de ir buscar los datos.
//
//Recuerden que justamente al ser un repositorio Esther, esta interfaz puede estar implementada en tanto
//
//un repositorio como en este caso Ještě, que es justamente el que va a buscar información al al servidor
//
//o haya implementado en otro repositorio que sea el encarga de buscar información localmente.
//
//Entonces de esta forma no sabe el ViewModel cuál de las dos implementaciones usar para esta clase.
//
//Entonces nosotros debemos proveerle desde el fragmento la implementación del interfaz que vamos
//
//a usar para traer los datos.
//
//
//Fíjense que pueden estar tanto los datos localmente como en un repositorio, entonces ese repositorio
//
//puede tener dos implementaciones distintas y puede tener una sola interfaz porque los métodos son los
//
//mismos, tanto para tener información localmente como del servidor.
//
Entonces es muy importante que marquemos la implementación de la interfaz acá.v
 */