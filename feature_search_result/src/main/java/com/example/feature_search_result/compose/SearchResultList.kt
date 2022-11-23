package com.example.feature_search_result.compose

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.example.core_design.compose.SingleColumnItem
import com.example.entity.BookInfo
import com.example.search_repository.SearchBooksPagingSource

@Composable
fun SearchResultList(
    state: SearchBooksPagingSource.SearchResultState,
    books: LazyPagingItems<BookInfo>,
    onClickItem: (BookInfo) -> Unit,
    onClickFavorite: (String, Boolean) -> Unit
) {
    LazyColumn {
        items(
            items = books,
            key = { it.id }
        ) { book ->
            book?.let {
                SingleColumnItem(
                    imageUrl = book.volumeInfo.images.imageUrl,
                    title = book.volumeInfo.title,
                    author = book.volumeInfo.author,
                    reviewTotalCount = book.volumeInfo.totalReviewCount,
                    reviewAverage = book.volumeInfo.averageReviewRate,
                    description = book.volumeInfo.description,
                    isFavorite = book.volumeInfo.isFavorite,
                    price = book.saleInfo.listPrice.price,
                    onClickItem = { onClickItem(book) },
                    onClickFavorite = { onClickFavorite(book.id, it) },
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
        }
    }
}