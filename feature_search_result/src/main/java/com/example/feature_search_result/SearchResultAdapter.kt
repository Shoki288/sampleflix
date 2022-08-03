package com.example.feature_search_result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.feature_search_result.databinding.ItemSearchResultBinding

class SearchResultAdapter : ListAdapter<SearchResult, SearchResultAdapter.SearchResultViewHolder>(diffCallback) {
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

    }

    inner class SearchResultViewHolder(val binding: ItemSearchResultBinding) : RecyclerView.ViewHolder(binding.root)
}

data class SearchResult(
    val title: String,
    val description: String,
    val imgUrl: String
)