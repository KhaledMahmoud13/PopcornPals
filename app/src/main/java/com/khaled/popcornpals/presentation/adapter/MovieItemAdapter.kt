package com.khaled.popcornpals.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.khaled.popcornpals.databinding.MovieItemBinding
import com.khaled.popcornpals.domain.model.Movie

class MovieItemAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Movie, MovieItemAdapter.MovieItemViewHolder>(DiffCallback) {

    class MovieItemViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.movie = movie
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(
            oldMovie: Movie,
            newMovie: Movie
        ): Boolean {
            return oldMovie == newMovie
        }

        override fun areContentsTheSame(
            oldMovie: Movie,
            newMovie: Movie
        ): Boolean {
            return oldMovie.id == newMovie.id
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        return MovieItemViewHolder(MovieItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        val movie = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(movie)
        }
        holder.bind(movie)

    }

    class OnClickListener(val clickListener: (movie: Movie) -> Unit) {
        fun onClick(movie: Movie) = clickListener(movie)
    }
}