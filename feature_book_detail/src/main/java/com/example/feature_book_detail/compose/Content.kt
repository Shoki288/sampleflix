package com.example.feature_book_detail.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core_design.R
import com.example.core_design.compose.StarsRating

@Composable
internal fun Content(
    modifier: Modifier,
    titleText: String,
    authorsText: String,
    averageReviewRateText: Int,
    totalReviewCountText: Int,
    categoriesText: String,
    priceText: Int,
) {
    Column(
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        // タイトル
        Text(
            text = titleText,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            maxLines = 2,
        )
        Spacer(modifier = Modifier.height(8.dp))

        // 著者
        Text(
            text = authorsText,
            maxLines = 2,
        )
        Spacer(modifier = Modifier.height(8.dp))

        // レビュー
        StarsRating(
            rateAverage = averageReviewRateText,
            totalAverage = totalReviewCountText,
        )
        Spacer(modifier = Modifier.height(8.dp))

        // カテゴリ
        Text(
            text = categoriesText,
            maxLines = 1,
        )
        Spacer(modifier = Modifier.height(8.dp))

        // 値段
        Text(
            text = stringResource(
                id = R.string.price,
                formatArgs = arrayOf(priceText)
            ),
            maxLines = 2,
        )
    }
}