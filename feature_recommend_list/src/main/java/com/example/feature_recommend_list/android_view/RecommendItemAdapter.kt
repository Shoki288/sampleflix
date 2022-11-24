package com.example.feature_recommend_list.android_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.entity.BookInfo
import com.example.feature_recommend_list.android_view.RecommendItemAdapter.RecommendItemViewHolder
import com.example.feature_recommend_list.databinding.ItemRecommendBinding

class RecommendItemAdapter(private val onItemClick: (BookInfo) -> Unit): ListAdapter<BookInfo, RecommendItemViewHolder>(diffCallback) {
    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<BookInfo>() {
            override fun areItemsTheSame(oldItem: BookInfo, newItem: BookInfo) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: BookInfo, newItem: BookInfo) = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RecommendItemViewHolder(ItemRecommendBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: RecommendItemViewHolder, position: Int) {
        holder.binding.bookInfo = getItem(position)
        holder.itemView.setOnClickListener { onItemClick(getItem(position) )}
    }

    class RecommendItemViewHolder(val binding: ItemRecommendBinding): RecyclerView.ViewHolder(binding.root)
}
