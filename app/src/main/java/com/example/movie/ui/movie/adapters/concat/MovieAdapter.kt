package com.example.movie.ui.movie.adapters.concat

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movie.core.BaseViewHolder
import com.example.movie.data.entities.Movie
import com.example.movie.databinding.MovieItemBinding
import com.example.movie.utils.MovieConstants

class MovieAdapter(private val movieList: List<Movie>, private val itemClickListener: onMovieClickListener ) : RecyclerView.Adapter<BaseViewHolder<*>>(){

    interface onMovieClickListener{
        fun onMovieClick(movie: Movie)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = MovieItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        val holder = MoviesViewHolder(itemBinding, parent.context)

        itemBinding.root.setOnClickListener {
            val position = holder.bindingAdapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                    ?: return@setOnClickListener
            itemClickListener.onMovieClick(movieList[position])
        }

        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is MoviesViewHolder -> holder.bind(movieList[position])
        }
    }

    override fun getItemCount(): Int = movieList.size

    private inner class MoviesViewHolder(val binding: MovieItemBinding, val context: Context): BaseViewHolder<Movie>(binding.root){
        override fun bind(item: Movie) {
            binding.imgMovie.load(MovieConstants.BASE_IMAGE+item.poster_path)
        }
    }
}