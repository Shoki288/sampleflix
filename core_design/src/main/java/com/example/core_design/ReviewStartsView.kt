package com.example.core_design

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.example.core_design.databinding.ReviewStartsViewBinding

class ReviewStartsView : FrameLayout {
    constructor(context: Context) : super(context) { init(context) }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) { init(context) }
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) { init(context) }

    private lateinit var binding: ReviewStartsViewBinding

    private fun init(context: Context) {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.review_starts_view, this, false)
        addView(binding.root)
    }

    internal fun setupViews(totalResult: Int, average: Int) {
        binding.totalReviews = totalResult
        binding.averageReview = average
    }
}

@BindingAdapter(value = ["setReviewTotalResult", "setReviewAverage"], requireAll = true)
fun ReviewStartsView.setReviewStars(totalResult: Int, average: Int) {
    setupViews(totalResult, average)
}

@BindingAdapter("starOrNot")
internal fun ImageView.setStarOrNot(isSetStar: Boolean) {
    setImageResource(if (isSetStar) R.drawable.ic_review_star else R.drawable.ic_not_review)
}