package com.example.feature_home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.entity.BookInfo
import com.example.feature_home.databinding.ItemRecommendBinding

class RecommendCarouselAdapter(
    private val listener: RecommendCarouselListener,
    private val viewModel: HomeViewModel
) : ListAdapter<BookInfo, RecommendCarouselAdapter.RecommendCarouselViewHolder>(diffCalBack) {
    companion object {
        private val diffCalBack = object : DiffUtil.ItemCallback<BookInfo>() {
            override fun areItemsTheSame(oldItem: BookInfo, newItem: BookInfo): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: BookInfo, newItem: BookInfo): Boolean = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendCarouselViewHolder {
        val binding = ItemRecommendBinding.inflate(LayoutInflater.from(parent.context))
        return RecommendCarouselViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendCarouselViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.imageUrl = item.bookInfo.images.imageUrl
        holder.binding.viewModel = viewModel

        holder.itemView.setOnClickListener {
            listener.onClickItem(item)
        }

        holder.binding.favoriteButton.setOnCheckedChangeListener { _, isCheck ->
            viewModel.updateFavoriteState(isCheck, item)
        }
    }

    class RecommendCarouselViewHolder(val binding: ItemRecommendBinding) : RecyclerView.ViewHolder(binding.root)

    interface RecommendCarouselListener {
        fun onClickItem(item: BookInfo)
    }
}