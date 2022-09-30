package com.example.feature_favorite.android_view

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.CompoundButton.OnCheckedChangeListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core_design.databinding.ItemBookInfoListBinding
import com.example.entity.BookInfo
import com.example.feature_favorite.android_view.FavoriteListAdapter.FavoriteListViewHolder

class FavoriteListAdapter(private val onClickFavorite: (BookInfo, Boolean) -> Unit, private val onClickItem: (BookInfo) -> Unit) : ListAdapter<BookInfo, FavoriteListViewHolder>(
    diffCallback
) {
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
        val item = getItem(position)
        holder.binding.apply {
            bookInfo = item
            onItemClickListener = OnClickListener { onClickItem(item) }
            onChangeFavoriteStateListener = OnCheckedChangeListener { _, isCheck ->
                onClickFavorite(item, isCheck)
            }
        }
    }

    inner class FavoriteListViewHolder(val binding: ItemBookInfoListBinding) : RecyclerView.ViewHolder(binding.root)
}