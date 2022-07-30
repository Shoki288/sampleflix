package com.example.sampleflix.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleflix.databinding.ItemRecommendBinding
import com.example.sampleflix.home.BookDetailBottomSheet.BookInfoItem

class RecommendCarouselAdapter(private val listener: RecommendCarouselListener) : ListAdapter<RecommendItem, RecommendCarouselAdapter.RecommendCarouselViewHolder>(diffCalBack) {
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
        val item = getItem(position)
        holder.binding.imageUrl = item.imgUrl

        holder.itemView.setOnClickListener {
            listener.onClickItem(
                BookInfoItem(
                    title = item.title,
                    publishDate = item.publishDate,
                    imageUrl = item.imgUrl,
                    description = item.description
                )
            )
        }
    }

    class RecommendCarouselViewHolder(val binding: ItemRecommendBinding) : RecyclerView.ViewHolder(binding.root)

    interface RecommendCarouselListener {
        fun onClickItem(item: BookInfoItem)
    }
}

data class RecommendItem(
    val id: String,
    val title: String,
    val publishDate: String,
    val description: String,
    val imgUrl: String,
)