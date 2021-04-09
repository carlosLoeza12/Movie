package com.example.movie.ui.movie.adapters.concat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.MoviePagedAdapter
import com.example.movie.core.BaseConcatHolder
import com.example.movie.databinding.UpcomingMovieRowBinding

class UpComingConcatAdapter(private val moviePagedAdapter: MoviePagedAdapter): RecyclerView.Adapter<BaseConcatHolder<*>>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseConcatHolder<*> {
        val itembinding = UpcomingMovieRowBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ConcatViewHolder(itembinding)
    }

    override fun onBindViewHolder(holder: BaseConcatHolder<*>, position: Int) {
        when(holder){
            is ConcatViewHolder-> holder.bind(moviePagedAdapter)
        }
    }

    override fun getItemCount(): Int =1
    //Es uno por que le estamos pasando solo un adapter

    private inner class ConcatViewHolder(val binding: UpcomingMovieRowBinding): BaseConcatHolder<MoviePagedAdapter>(binding.root){
        override fun bind(moviePagedAdapter: MoviePagedAdapter) {
           //binding.rvUpcomingMovies.scrollToPosition(p)
            binding.rvUpcomingMovies.adapter = moviePagedAdapter
        }
    }
}