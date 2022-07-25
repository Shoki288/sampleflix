package com.example.sampleflix.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sampleflix.R
import com.example.sampleflix.databinding.ItemRecommendBinding

class RecommendCarouselAdapter : ListAdapter<RecommendItem, RecommendCarouselAdapter.RecommendCarouselViewHolder>(diffCalBack) {
    companion object {
        private val diffCalBack = object : DiffUtil.ItemCallback<RecommendItem>() {
            override fun areItemsTheSame(oldItem: RecommendItem, newItem: RecommendItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: RecommendItem,
                newItem: RecommendItem
            ): Boolean = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendCarouselViewHolder {
        val binding = ItemRecommendBinding.inflate(LayoutInflater.from(parent.context))
        return RecommendCarouselViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendCarouselViewHolder, position: Int) {
        println(getItem(position).imgUrl)

        Glide.with(holder.binding.root.context)
            .load(getItem(position).imgUrl)
            .error(R.drawable.ic_not_found_image)
            .fallback(R.drawable.ic_not_found_image)
            .fallback(R.drawable.ic_not_found_image)
            .into(holder.binding.bookImage)
    }

    class RecommendCarouselViewHolder(val binding: ItemRecommendBinding) : RecyclerView.ViewHolder(binding.root)
}

data class RecommendItem(
    val id: String,
    val imgUrl: String,
)