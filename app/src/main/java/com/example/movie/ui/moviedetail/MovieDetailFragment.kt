package com.example.movie.ui.moviedetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.movie.R
import com.example.movie.databinding.FragmentDetailMovieBinding
import com.example.movie.utils.MovieConstants


class MovieDetailFragment : Fragment(R.layout.fragment_detail_movie) {

    lateinit var binding: FragmentDetailMovieBinding
    private val arguments by navArgs<MovieDetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailMovieBinding.bind(view)

        binding.imgMovie.load(MovieConstants.BASE_IMAGE+arguments.posterImageURL)
        binding.imgBackground.load(MovieConstants.BASE_IMAGE+arguments.backgroundImageURL)
        binding.txtDescription.text = arguments.overview
        binding.txtTitle.text = arguments.title
        binding.txtRelease.text = arguments.releaseDate
        binding.txtLanguaje.text =   getString(R.string.Language)+arguments.language
        binding.txtRating.text = "${arguments.voteAverage} (${arguments.VoteCount})"+ getString(R.string.Reviews)
    }
}