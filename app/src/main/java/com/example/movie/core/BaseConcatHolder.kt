package com.example.movie.core

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseConcatHolder<T>(itemVie: View) : RecyclerView.ViewHolder(itemVie) {
    abstract fun bind(adapter: T)
}