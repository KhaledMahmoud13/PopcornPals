package com.khaled.popcornpals.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.khaled.popcornpals.data.model.Actor
import com.khaled.popcornpals.databinding.ActorItemBinding

class ActorItemAdapter : ListAdapter<Actor, ActorItemAdapter.ActorItemViewHolder>(DiffCallback) {

    class ActorItemViewHolder(private val binding: ActorItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(actor: Actor) {
            binding.actor = actor
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Actor>() {
        override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorItemViewHolder {
        return ActorItemViewHolder(ActorItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ActorItemViewHolder, position: Int) {
        val actor = getItem(position)
        holder.bind(actor)
    }
}