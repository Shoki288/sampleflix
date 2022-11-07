package com.example.core_design.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.core_design.R

@Composable
fun StarsRating(
    rateAverage: Int,
    totalAverage: Int,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        modifier = modifier
            .wrapContentWidth()
            .height(24.dp)
    ) {
        repeat(5) {
            Image(
                painter = painterResource(id = if (rateAverage > it) R.drawable.ic_review_star else R.drawable.ic_not_review),
                modifier = modifier.align(Alignment.CenterVertically),
                contentDescription = null
            )
        }
        Text(
            text = totalAverage.toString(),
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
        )
    }
}