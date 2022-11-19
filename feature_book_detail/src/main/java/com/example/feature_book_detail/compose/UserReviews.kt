package com.example.feature_book_detail.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core_design.compose.StarsRating
import com.example.feature_book_detail.R

@Composable
internal fun UserReviews(
    modifier: Modifier,
    averageReviewRate: Int,
    totalReviewCount: Int
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.customer_review_title),
            fontWeight = FontWeight.Medium,
            modifier = Modifier.align(Alignment.Start),
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        StarsRating(
            rateAverage = averageReviewRate,
            totalAverage = totalReviewCount,
        )
    }
}