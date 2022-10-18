package com.example.feature_home.compose.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.entity.BookInfo
import com.example.entity.Price
import com.example.entity.SaleInfo
import com.example.entity.VolumeInfo
import com.example.extension.R

@Composable
fun RecommendBooks(
    title: String,
    books: List<BookInfo>,
    onClickItem: (BookInfo) -> Unit,
    onClickShowAll: (List<BookInfo>) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(size = 20.dp),
        border = BorderStroke(
            width = 2.dp,
            color = MaterialTheme.colorScheme.outline
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            // タイトル
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(start = 8.dp)
            )

            RecommendBookCarousel(
                books = books,
                onClickItem = onClickItem
            )

            // リンク
            LinkText {
                onClickShowAll(books)
            }
        }
    }
}

@Composable
private fun RecommendBookCarousel(
    books: List<BookInfo>,
    onClickItem: (BookInfo) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            items = books,
            key = { bookInfo ->
                bookInfo.id
            }
        ) { bookInfo ->
            // 商品画像
            AsyncImage(
                model = bookInfo.volumeInfo.images.imageUrl,
                placeholder = painterResource(id = R.drawable.ic_not_found_image),
                error = painterResource(id = R.drawable.ic_not_found_image),
                fallback = painterResource(id = R.drawable.ic_not_found_image),
                contentDescription = null,
                modifier = Modifier
                    .width(150.dp)
                    .height(200.dp)
                    .clickable {
                        onClickItem(bookInfo)
                    }
            )
        }
    }
}

@Composable
private fun LinkText(
    onClick: () -> Unit
) {
    Text(
        text = "すべて表示",
        color = Color.Blue,
        modifier = Modifier
            .clickable { onClick() }
            .padding(bottom = 8.dp)
    )
}

@Preview
@Composable
fun Preview() {
    RecommendBooks(
        title = "title",
        books = listOf(
            BookInfo(
                id = "id",
                volumeInfo = VolumeInfo(
                    title = "title",
                    authors = listOf("authors"),
                    publisher = "publisher",
                    publishedDate = "publishedDate",
                    description = "description",
                    pageCount = 100,
                    categories = listOf("categories"),
                    averageRating = 100,
                    ratingCount = 100,
                    language = "language",
                    previewLink = "previewLink",
                    isFavorite = false,
                ),
                saleInfo = SaleInfo(
                    listPrice = Price(10000)
                ),
                accessInfo = null
            )
        ),
        onClickItem = {},
        onClickShowAll = {}
    )
}