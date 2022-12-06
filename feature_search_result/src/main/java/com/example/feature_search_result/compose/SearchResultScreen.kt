package com.example.feature_search_result.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.entity.*
import com.example.feature_search_result.SearchResultViewModel
import com.example.search_repository.SearchBooksPagingSource.SearchResultState
import kotlinx.coroutines.flow.flowOf

@Composable
fun SearchResultRoot(
    viewModel: SearchResultViewModel = hiltViewModel(),
    arg: String?,
    onClickItem: (BookInfo) -> Unit
) {
    val keyword = rememberSaveable { mutableStateOf(arg ?: "") }

    SearchResultScreen(
        keyword = keyword,
        state = viewModel.searchResultState.collectAsState().value,
        pagingItems = viewModel.searchResult.collectAsLazyPagingItems(),
        onClickItem = onClickItem,
        onClickFavorite = { bookInfo, isFavorite -> viewModel.updateFavoriteState(isFavorite, bookInfo) }
    )
}
@Composable
fun SearchResultScreen(
    keyword: MutableState<String>,
    state: SearchResultState,
    pagingItems: LazyPagingItems<BookInfo>,
    onClickItem: (BookInfo) -> Unit,
    onClickFavorite: (BookInfo, Boolean) -> Unit
) {
    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        SearchBox(
            value = keyword.value,
            onChangeValue = { keyword.value = it }
        )
        Spacer(modifier = Modifier.height(4.dp))
        SearchResultList(
            state = state,
            books = pagingItems,
            onClickItem = onClickItem,
            onClickFavorite = onClickFavorite
        )
    }
}

@Composable
@Preview
fun Preview() {
    SearchResultScreen(
        keyword = remember { mutableStateOf("keyword") },
        state = SearchResultState.SUCCESS,
        pagingItems = flowOf(
            PagingData.from((0..10).map {
                BookInfo(
                    id = "id$it",
                    volumeInfo = VolumeInfo(
                        title = "title",
                        authors = emptyList(),
                        publisher = "publisher",
                        publishedDate = "publishedDate",
                        description = "description",
                        pageCount = 0,
                        categories = emptyList(),
                        averageRating = 0,
                        ratingCount = 0,
                        images = ImageLinks(thumbnail = "thumbanail"),
                        language = "language",
                        previewLink = "previewLink",
                        isFavorite = false
                    ),
                    saleInfo = SaleInfo(listPrice = Price(price = 100)),
                    accessInfo = AccessInfo(null)
                )
            })
        ).collectAsLazyPagingItems(),
        onClickItem = {},
        onClickFavorite = { id, isFavorite -> }
    )
}