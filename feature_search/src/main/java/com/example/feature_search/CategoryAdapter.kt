package com.example.feature_search

import com.example.core_design.R
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.feature_search.databinding.ItemSearchCategoryBinding

class CategoryAdapter(val onClick: (String) -> Unit): ListAdapter<String, CategoryAdapter.CategoryViewHolder>(diffCallback) {
    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = false
        }
        private val colorList = listOf(R.color.pink, R.color.purple, R.color.deep_purple, R.color.blue,
            R.color.light_blue, R.color.green, R.color.light_green, R.color.lime, R.color.gray, R.color.orange)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(ItemSearchCategoryBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.binding.title = getItem(position)
        val resources = holder.binding.root.resources
        holder.binding.card.setCardBackgroundColor(resources.getColor(colorList[position%colorList.size], null))
        holder.binding.root.setOnClickListener { onClick(getItem(position)) }
    }

    class CategoryViewHolder(val binding: ItemSearchCategoryBinding): RecyclerView.ViewHolder(binding.root)

}