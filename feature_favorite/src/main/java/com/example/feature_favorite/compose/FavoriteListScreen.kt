package com.example.feature_favorite.compose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core_design.compose.SingleColumnItem
import com.example.core_unit_test.fixture.createBookInfo
import com.example.entity.BookInfo
import com.example.feature_favorite.FavoriteListViewModel
import com.example.feature_favorite.R
import com.example.feature_favorite.android_view.FavoriteListUiState

@Composable
fun FavoriteListRoute(
    viewModel: FavoriteListViewModel = hiltViewModel(),
    onClickItem: (BookInfo) -> Unit
) {
    when (val uiState = viewModel.favoriteList.collectAsState().value) {
        is FavoriteListUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        is FavoriteListUiState.Success -> {
            FavoriteListScreen(
                bookInfoList = uiState.bookInfoList,
                onClickItem = onClickItem,
                onClickFavorite = { bookInfo, isFavorite -> }
            )
        }
        is FavoriteListUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = stringResource(id = R.string.empty_favorite_list),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
private fun FavoriteListScreen(
    bookInfoList: List<BookInfo>,
    onClickFavorite: (BookInfo, Boolean) -> Unit,
    onClickItem: (BookInfo) -> Unit
) {
    Column {
        Text(
            text = stringResource(id = R.string.favorite_title),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )
        Divider()
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            items(
                items = bookInfoList,
                key = { bookInfo -> bookInfo.id }
            ) { bookInfo ->
                SingleColumnItem(
                    imageUrl = bookInfo.volumeInfo.images.imageUrl,
                    title = bookInfo.volumeInfo.title,
                    author = bookInfo.volumeInfo.author,
                    reviewTotalCount = bookInfo.volumeInfo.totalReviewCount,
                    reviewAverage = bookInfo.volumeInfo.averageReviewRate,
                    description = bookInfo.volumeInfo.description,
                    isFavorite = bookInfo.volumeInfo.isFavorite,
                    price = bookInfo.saleInfo.listPrice.price,
                    onClickItem = { onClickItem(bookInfo) },
                    onClickFavorite = { }
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    FavoriteListScreen(
        (0..10).map { createBookInfo(id = "id$it") },
        onClickFavorite = { info, isFavorite -> },
        onClickItem = {}
    )
}