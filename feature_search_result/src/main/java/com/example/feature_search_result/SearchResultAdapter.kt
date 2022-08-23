package com.example.feature_search_result

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.core_design.databinding.ItemBookInfoListBinding
import com.example.entity.BookInfo
import com.example.feature_search_result.SearchResultAdapter.SearchResultViewHolder

class SearchResultAdapter(
    private val onClickItem: (BookInfo) -> Unit,
    private val onClickFavorite: (BookInfo) -> Unit
) : PagingDataAdapter<BookInfo, SearchResultViewHolder>(diffCallback) {
    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<BookInfo>() {
            override fun areItemsTheSame(oldItem: BookInfo, newItem: BookInfo): Boolean =
                newItem == oldItem
            override fun areContentsTheSame(oldItem: BookInfo, newItem: BookInfo): Boolean =
                newItem.id == oldItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        return SearchResultViewHolder(
            ItemBookInfoListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.binding.apply {
            thumbnailUrl = item.bookInfo.images?.imageUrl ?: ""
            title = item.bookInfo.title
            author = item.bookInfo.author
            publisher = item.bookInfo.publisher ?: "不明"
            reviewTotalResult = item.bookInfo.totalReviewCount
            reviewAverageResult = item.bookInfo.averageReviewRate
            description = item.bookInfo.description
            price = item.salesInfo.listPrice.price
            onClickFavorite = View.OnClickListener {
                onClickFavorite(item)
            }
        }

        holder.itemView.setOnClickListener { onClickItem(item) }
    }

    inner class SearchResultViewHolder(val binding: ItemBookInfoListBinding) :
        RecyclerView.ViewHolder(binding.root)
}