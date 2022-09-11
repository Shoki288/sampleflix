package com.example.feature_home

import android.content.res.ColorStateList
import android.graphics.Color.blue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core_design.R.*
import com.example.feature_home.databinding.ItemRecommendCategoryBinding

class RecommendCategoryAdapter: ListAdapter<String, RecommendCategoryAdapter.RecommendCategoryViewHolder>(diffCallBack) {
    companion object {
        private val diffCallBack = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = false
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendCategoryViewHolder {
        return RecommendCategoryViewHolder(
            ItemRecommendCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecommendCategoryViewHolder, position: Int) {
        holder.binding.title.text = getItem(position)
        val color = holder.itemView.resources.obtainTypedArray(R.array.colors).getColor(position, 0)
        holder.binding.categoryCard.backgroundTintList = ColorStateList.valueOf(color)
    }

    class RecommendCategoryViewHolder(val binding: ItemRecommendCategoryBinding): RecyclerView.ViewHolder(binding.root)
}