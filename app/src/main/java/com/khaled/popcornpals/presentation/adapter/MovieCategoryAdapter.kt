package com.khaled.popcornpals.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.khaled.popcornpals.domain.model.Category
import com.khaled.popcornpals.databinding.ParentRecyclerviewItemBinding

class MovieCategoryAdapter(private val onClickListener: MovieItemAdapter.OnClickListener) :
    ListAdapter<Category, MovieCategoryAdapter.MovieCategoryViewHolder>(DiffCallback) {

    class MovieCategoryViewHolder(var binding: ParentRecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) {
            binding.category = category
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(
            oldMovie: Category,
            newMovie: Category
        ): Boolean {
            return oldMovie == newMovie
        }

        override fun areContentsTheSame(
            oldMovie: Category,
            newMovie: Category
        ): Boolean {
            return oldMovie.id == newMovie.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCategoryViewHolder {
        return MovieCategoryViewHolder(
            ParentRecyclerviewItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: MovieCategoryViewHolder, position: Int) {
        val category = getItem(position)
        val movieItemAdapter = MovieItemAdapter(onClickListener)
        holder.bind(category)

        holder.binding.movieList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = movieItemAdapter
        }
        movieItemAdapter.submitList(category.movies)
    }
}