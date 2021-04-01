package com.example.movie.ui.movie.adapters.concat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.core.BaseConcatHolder
import com.example.movie.databinding.PopularMoviesRowBinding


class PopularConcatAdapter(private val movieAdapter: MovieAdapter, private val p: Int): RecyclerView.Adapter<BaseConcatHolder<*>>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseConcatHolder<*> {
        val itembinding = PopularMoviesRowBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ConcatViewHolder(itembinding)

    }

    override fun onBindViewHolder(holder: BaseConcatHolder<*>, position: Int) {
        when(holder){
            is ConcatViewHolder-> holder.bind(movieAdapter)
        }
    }

    override fun getItemCount(): Int =1
    //Es uno por que le estamos pasando solo un adapter
    private inner class ConcatViewHolder(val binding: PopularMoviesRowBinding): BaseConcatHolder<MovieAdapter>(binding.root){
        override fun bind(adapter: MovieAdapter) {
            binding.rvPopularMovies.scrollToPosition(p)
            binding.rvPopularMovies.adapter = adapter
        }
    }
}

