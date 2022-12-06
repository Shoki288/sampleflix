package com.example.feature_search_result.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
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
    onClickFavorite: (BookInfo, Boolean) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
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
                    onClickFavorite = { onClickFavorite(book, it) },
                )
            }
        }
    }
}