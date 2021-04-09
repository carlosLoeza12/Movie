package com.example.movie

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import coil.load
import com.example.movie.core.BaseViewHolder
import com.example.movie.data.model.Movie
import com.example.movie.databinding.MovieItemBinding
import com.example.movie.utils.MovieConstants

class MoviePagedAdapter (): PagedListAdapter<Movie, BaseViewHolder<*>>(MovieDiffUtilCallBack()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = MovieItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        val holder = MoviesViewHolder(itemBinding, parent.context)
        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is MoviePagedAdapter.MoviesViewHolder -> holder.bind(getItem(position)!!)
        }
    }

    private inner class MoviesViewHolder(val binding: MovieItemBinding, val context: Context): BaseViewHolder<Movie>(binding.root){

        override fun bind(item: Movie) {
            binding.imgMovie.load(MovieConstants.BASE_IMAGE+item.poster_path)
        }
    }

}