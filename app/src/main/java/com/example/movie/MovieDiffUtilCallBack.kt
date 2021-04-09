package com.example.movie

import androidx.recyclerview.widget.DiffUtil
import com.example.movie.data.model.Movie

class MovieDiffUtilCallBack: DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id &&oldItem.title == newItem.title
    }

}