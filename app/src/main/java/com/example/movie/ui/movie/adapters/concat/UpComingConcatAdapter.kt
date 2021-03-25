package com.example.movie.ui.movie.adapters.concat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.core.BaseConcatHolder
import com.example.movie.databinding.UpcomingMovieRowBinding

class UpComingConcatAdapter(private val movieAdapter: MovieAdapter): RecyclerView.Adapter<BaseConcatHolder<*>>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseConcatHolder<*> {
        val itembinding = UpcomingMovieRowBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ConcatViewHolder(itembinding)
    }

    override fun onBindViewHolder(holder: BaseConcatHolder<*>, position: Int) {
        when(holder){
            is ConcatViewHolder-> holder.bind(movieAdapter)
        }
    }

    override fun getItemCount(): Int =1
    //Es uno por que le estamos pasando solo un adapter

    private inner class ConcatViewHolder(val binding: UpcomingMovieRowBinding): BaseConcatHolder<MovieAdapter>(binding.root){
        override fun bind(adapter: MovieAdapter) {
            binding.rvUpcomingMovies.adapter = adapter
        }
    }
}