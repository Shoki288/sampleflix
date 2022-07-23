package com.example.sampleflix.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleflix.databinding.ItemRecommendBinding

class RecommendCarousel: ListAdapter<RecommendItem, RecommendCarousel.RecommendCarouselViewHolder>(diffCalBack) {
    companion object {
        private val diffCalBack = object : DiffUtil.ItemCallback<RecommendItem>() {
            override fun areItemsTheSame(oldItem: RecommendItem, newItem: RecommendItem): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: RecommendItem, newItem: RecommendItem): Boolean = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendCarouselViewHolder {
        val binding = ItemRecommendBinding.inflate(LayoutInflater.from(parent.context))
        return RecommendCarouselViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendCarouselViewHolder, position: Int) {
        holder.binding.bookImage
    }

    class RecommendCarouselViewHolder(val binding: ItemRecommendBinding): RecyclerView.ViewHolder(binding.root)
}

data class RecommendItem(
    val id: String,
    val imgUrl: String,
)