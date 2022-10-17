package com.example.feature_home.compose

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.entity.BookInfo
import com.example.extension.R
import com.example.feature_home.HomeViewModel

@Preview
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 8.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // 読み始めたシリーズを続ける
        RecommendCarousel(
            title = "読み始めたシリーズを続ける",
            books = viewModel.recentlyReadingBooks.collectAsState().value,
            onClickItem = {
                Toast.makeText(context, "onClickItem", Toast.LENGTH_SHORT).show()
            },
            onClickShowAll = {
                Toast.makeText(context, "onClickShowAll", Toast.LENGTH_SHORT).show()
            }
        )

        // すぐ読める本
        RecommendCarousel(
            title = "すぐ読める本",
            books = viewModel.recommendBooks.collectAsState().value,
            onClickItem = {
                Toast.makeText(context, "onClickItem", Toast.LENGTH_SHORT).show()
            },
            onClickShowAll = {
                Toast.makeText(context, "onClickShowAll", Toast.LENGTH_SHORT).show()
            }
        )

        // プライム会員特定で読めるベストセラー
        RecommendCarousel(
            title = "プライム会員特定で読めるベストセラー",
            books = viewModel.bestSellerBooks.collectAsState().value,
            onClickItem = {
                Toast.makeText(context, "onClickItem", Toast.LENGTH_SHORT).show()
            },
            onClickShowAll = {
                Toast.makeText(context, "onClickShowAll", Toast.LENGTH_SHORT).show()
            }
        )

        // 最近読んだ本に基づくおすすめ
        RecommendCarousel(
            title = "最近読んだ本に基づくおすすめ",
            books = viewModel.recentlyReadHistoryBooks.collectAsState().value,
            onClickItem = {
                Toast.makeText(context, "onClickItem", Toast.LENGTH_SHORT).show()
            },
            onClickShowAll = {
                Toast.makeText(context, "onClickShowAll", Toast.LENGTH_SHORT).show()
            }
        )

        // もうすぐ読み放題が終了するタイトル
        RecommendCarousel(
            title = "もうすぐ読み放題が終了するタイトル",
            books = viewModel.endUnlimitedReadingBooks.collectAsState().value,
            onClickItem = {
                Toast.makeText(context, "onClickItem", Toast.LENGTH_SHORT).show()
            },
            onClickShowAll = {
                Toast.makeText(context, "onClickShowAll", Toast.LENGTH_SHORT).show()
            }
        )

        // 近日配信開始のタイトルのおすすめ
        RecommendCarousel(
            title = "近日配信開始のタイトルのおすすめ",
            books = viewModel.recentlyReleaseBooks.collectAsState().value,
            onClickItem = {
                Toast.makeText(context, "onClickItem", Toast.LENGTH_SHORT).show()
            },
            onClickShowAll = {
                Toast.makeText(context, "onClickShowAll", Toast.LENGTH_SHORT).show()
            }
        )

        // 類似タイトルに基づくおすすめ
        RecommendCarousel(
            title = "類似タイトルに基づくおすすめ",
            books = viewModel.similarTitleBooks.collectAsState().value,
            onClickItem = {
                Toast.makeText(context, "onClickItem", Toast.LENGTH_SHORT).show()
            },
            onClickShowAll = {
                Toast.makeText(context, "onClickShowAll", Toast.LENGTH_SHORT).show()
            }
        )

        // 読書履歴に基づくおすすめ
        RecommendCarousel(
            title = "読書履歴に基づくおすすめ",
            books = viewModel.readingHistoryBooks.collectAsState().value,
            onClickItem = {
                Toast.makeText(context, "onClickItem", Toast.LENGTH_SHORT).show()
            },
            onClickShowAll = {
                Toast.makeText(context, "onClickShowAll", Toast.LENGTH_SHORT).show()
            }
        )

        // 本をさらに見る

    }
}

@Composable
fun RecommendCarousel(
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

            RecommendItem(
                books = books,
                onClickItem = onClickItem
            )

            // リンク
            LinkText(
                text = "すべて表示",
                onClick = { onClickShowAll(books) }
            )
        }
    }
}

@Composable
fun RecommendItem(
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
            println("imageUrl: ${bookInfo.volumeInfo.images.imageUrl}")
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
fun LinkText(
    text: String,
    onClick: () -> Unit
) {
    Text(
        text = text,
        color = Color.Blue,
        modifier = Modifier
            .clickable { onClick() }
            .padding(bottom = 8.dp)
    )
}