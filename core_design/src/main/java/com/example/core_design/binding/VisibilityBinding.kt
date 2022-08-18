package com.example.core_design.binding

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("isVisibleOrGone")
fun View.visibleOrGone(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}