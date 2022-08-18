package com.example.core_design.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.extension.R

@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(imageUrl: String) {
    Glide.with(this.context)
        .load(imageUrl)
        .error(R.drawable.ic_not_found_image)
        .fallback(R.drawable.ic_not_found_image)
        .placeholder(R.drawable.ic_not_found_image)
        .into(this)
}