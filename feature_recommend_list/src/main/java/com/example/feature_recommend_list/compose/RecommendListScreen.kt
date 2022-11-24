package com.example.feature_recommend_list.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_design.compose.ThreeColumnList
import com.example.entity.*

@Composable
fun RecommendListScreen(
    title: String,
    bookInfoList: List<BookInfo>
) {
    Column {
        // タイトル
        Text(
            text = title,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )

        Divider()

        ThreeColumnList(
            bookInfoList = bookInfoList
        )
    }
}

@Preview
@Composable
private fun Preview() {
    RecommendListScreen(
        title = "title,title,title,title,title,title,",
        (0..10).map {
            BookInfo(
                id = "id$it",
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