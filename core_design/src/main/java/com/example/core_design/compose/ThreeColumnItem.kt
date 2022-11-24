package com.example.core_design.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_design.R
import com.example.entity.*

@Composable
fun ThreeColumnList(
    bookInfoList: List<BookInfo>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 3),
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items(
            count = bookInfoList.size
        ) { index ->
            val bookInfo = bookInfoList[index]
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                // サムネ画像
                DefaultAsyncImage(
                    imageUrl = bookInfo.volumeInfo.images.imageUrl,
                    modifier = Modifier.width(150.dp).height(100.dp)
                )
                // タイトル
                Text(
                    text = bookInfo.volumeInfo.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                // レビュー
                StarsRating(
                    rateAverage = bookInfo.volumeInfo.averageReviewRate,
                    totalAverage = bookInfo.volumeInfo.totalReviewCount
                )
                // 値段
                Text(text = stringResource(id = R.string.price, bookInfo.saleInfo.listPrice.price))
            }
        }
    }
}

@Composable
@Preview
private fun Preview() {
    ThreeColumnList(
        (1..10).map {
            BookInfo(
                id = "Id",
                volumeInfo = VolumeInfo(
                    title = "title",
                    authors = listOf("authors"),
                    publisher = "publisher",
                    publishedDate = "publishedDate",
                    description = "description",
                    pageCount = 100,
                    categories = listOf("category"),
                    averageRating = 100,
                    ratingCount = 100,
                    images = ImageLinks(),
                    language = "language",
                    previewLink = "previewLink",
                    isFavorite = true,
                ),
                saleInfo = SaleInfo(
                    listPrice = Price(price = 1000)
                ),
                accessInfo = null,
            )
        }
    )
}