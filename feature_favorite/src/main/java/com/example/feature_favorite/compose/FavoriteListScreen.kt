package com.example.feature_favorite.compose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core_design.compose.SingleColumnItem
import com.example.entity.BookInfo
import com.example.entity.CacheBookInfo
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
                onClickFavorite = { bookInfo, isFavorite -> },
                onClickItem = {  }
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
    bookInfoList: List<CacheBookInfo>,
    onClickFavorite: (BookInfo, Boolean) -> Unit,
    onClickItem: (BookInfo) -> Unit
) {
    Column {
        Text(
            text = stringResource(id = R.string.favorite_title),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 16.dp)
        )
        LazyColumn {
            items(
                items = bookInfoList,
                key = { it.id }
            ) { bookInfo ->
                SingleColumnItem(
                    imageUrl = bookInfo.image,
                    title = bookInfo.title,
                    author = bookInfo.authors,
                    reviewTotalCount = bookInfo.ratingCount,
                    reviewAverage = bookInfo.averageRating ?: 0,
                    description = bookInfo.description,
                    isFavorite = bookInfo.isFavorite,
                    price = bookInfo.price,
                    onClickItem = { },
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
        listOf(
            CacheBookInfo(
                id = "id",
                title = "title",
                authors = "authors",
                publisher = "publisher",
                publishedDate = "publishedDate",
                description = "description",
                pageCount = 100,
                categories = "categories",
                averageRating = 100,
                ratingCount = 100,
                image = "image",
                language = "language",
                previewLink = "previewLink",
                price = 100,
                isFavorite = true,
            )
        ),
        onClickFavorite = { info, isFavorite -> },
        onClickItem = {}
    )
}