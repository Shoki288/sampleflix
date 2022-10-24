package com.example.feature_search_result.android_view

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.CompoundButton.OnCheckedChangeListener
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.core_design.databinding.ItemBookInfoListBinding
import com.example.entity.BookInfo
import com.example.feature_search_result.android_view.SearchResultAdapter.SearchResultViewHolder

class SearchResultAdapter(
    private val onClickItem: (BookInfo) -> Unit,
    private val onClickFavorite: (BookInfo, Boolean) -> Unit
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
            bookInfo = item
            onItemClickListener = OnClickListener { onClickItem(item) }
            onChangeFavoriteStateListener = OnCheckedChangeListener { _, isCheck ->
                onClickFavorite(item, isCheck)
            }
        }
    }

    inner class SearchResultViewHolder(val binding: ItemBookInfoListBinding) :
        RecyclerView.ViewHolder(binding.root)
}