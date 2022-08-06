package com.example.feature_search_result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.feature_search_result.SearchResultAdapter.SearchResultViewHolder
import com.example.feature_search_result.databinding.ItemSearchResultBinding

class SearchResultAdapter : ListAdapter<SearchResult, SearchResultViewHolder>(diffCallback) {
    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<SearchResult>() {
            override fun areItemsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean =
                newItem == oldItem
            override fun areContentsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean =
                newItem.title == oldItem.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        return SearchResultViewHolder(
            ItemSearchResultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            thumbnailUrl = item.imgUrl
            title = item.title
            author = item.author
            publisher = item.publisher
            reviewTotalResult = item.reviewTotalResult
            reviewAverageResult = item.reviewAverageResult
            description = item.description
            price = item.price
        }
    }

    inner class SearchResultViewHolder(val binding: ItemSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root)
}

data class SearchResult(
    val imgUrl: String,
    val title: String,
    val price: Int,
    val description: String,
    val author: String,
    val publisher: String,
    val reviewTotalResult: Int,
    val reviewAverageResult: Int,
)