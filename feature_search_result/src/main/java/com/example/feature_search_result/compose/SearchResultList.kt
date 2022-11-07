package com.example.feature_search_result.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.example.core_design.compose.StarsRating
import com.example.entity.BookInfo
import com.example.extension.R
import com.example.search_repository.SearchBooksPagingSource

@Composable
fun SearchResultList(
    state: SearchBooksPagingSource.SearchResultState,
    books: LazyPagingItems<BookInfo>,
    onClickItem: (BookInfo) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(4.dp)
    ) {
        items(
            items = books,
            key = { it.id }
        ) { book ->
            book?.let {
                SearchResultItem(
                    info = it,
                    onClickItem = onClickItem
                )
            }
        }
    }
}

@Composable
fun SearchResultItem(
    info: BookInfo,
    onClickItem: (BookInfo) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.clickable { onClickItem(info) }
    ) {
        // サムネイル
        AsyncImage(
            model = info.volumeInfo.images.imageUrl,
            placeholder = painterResource(id = R.drawable.ic_not_found_image),
            error = painterResource(id = R.drawable.ic_not_found_image),
            fallback = painterResource(id = R.drawable.ic_not_found_image),
            contentDescription = null,
            modifier = Modifier
                .width(150.dp)
                .height(200.dp)
        )

        SearchResultItemBody(info = info)
    }
}

@Composable
fun SearchResultItemBody(
    info: BookInfo
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // タイトル
        Text(text = info.volumeInfo.title)
        // 著者
        Text(text = info.volumeInfo.author)
        // レビュー
        StarsRating(
            rateAverage = info.volumeInfo.averageReviewRate,
            totalAverage = info.volumeInfo.totalReviewCount
        )
        // 説明
        Text(text = info.volumeInfo.description, maxLines = 3)
        // 値段
        Text(
            text = stringResource(
                id = com.example.core_design.R.string.price,
                info.saleInfo.listPrice.price
            )
        )
    }
}