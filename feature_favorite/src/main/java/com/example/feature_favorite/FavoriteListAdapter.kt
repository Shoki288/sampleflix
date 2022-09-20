package com.example.feature_favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core_design.databinding.ItemBookInfoListBinding
import com.example.entity.BookInfo
import com.example.feature_favorite.FavoriteListAdapter.FavoriteListViewHolder

class FavoriteListAdapter : ListAdapter<BookInfo, FavoriteListViewHolder>(diffCallback) {
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<BookInfo>() {
            override fun areItemsTheSame(oldItem: BookInfo, newItem: BookInfo): Boolean =
                oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: BookInfo, newItem: BookInfo): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteListViewHolder {
        return FavoriteListViewHolder(
            ItemBookInfoListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavoriteListViewHolder, position: Int) {
        val bookInfo = getItem(position).volumeInfo
        val salesInfo = getItem(position).saleInfo
        holder.binding.apply {
            thumbnailUrl = bookInfo.images.imageUrl
            title = bookInfo.title
            author = bookInfo.author
            publisher = bookInfo.publisher
            reviewTotalResult = bookInfo.totalReviewCount
            reviewAverageResult = bookInfo.averageReviewRate
            description = bookInfo.description
            price = salesInfo.listPrice.price
            onClickFavorite = View.OnClickListener {}
        }
    }

    inner class FavoriteListViewHolder(val binding: ItemBookInfoListBinding) : RecyclerView.ViewHolder(binding.root)
}