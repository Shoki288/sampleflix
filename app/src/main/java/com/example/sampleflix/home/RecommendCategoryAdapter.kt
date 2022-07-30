package com.example.sampleflix.home

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleflix.R
import com.example.sampleflix.databinding.ItemRecommendCategoryBinding

class RecommendCategoryAdapter: ListAdapter<String, RecommendCategoryAdapter.RecommendCategoryViewHolder>(diffCallBack) {
    companion object {
        private val diffCallBack = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = false
            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = false
        }

        private val colorList = listOf(R.color.pink, R.color.purple, R.color.deep_purple, R.color.blue,
            R.color.light_blue, R.color.green, R.color.light_green, R.color.lime, R.color.gray, R.color.orange)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendCategoryViewHolder {
        return RecommendCategoryViewHolder(
            ItemRecommendCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecommendCategoryViewHolder, position: Int) {
        holder.binding.title.text = getItem(position)
        val color = holder.itemView.resources.getColor(colorList.getOrElse(position){ R.color.black }, null)
        holder.binding.categoryCard.backgroundTintList = ColorStateList.valueOf(color)

    }

    class RecommendCategoryViewHolder(val binding: ItemRecommendCategoryBinding): RecyclerView.ViewHolder(binding.root)
}