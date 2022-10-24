package com.example.feature_search_result.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.example.core_design.R.drawable
import com.example.entity.BookInfo
import com.example.extension.R
import com.example.feature_search_result.SearchResultViewModel
import com.example.search_repository.SearchBooksPagingSource.SearchResultState

@Composable
fun SearchResultScreen(
    arg: String?,
    viewModel: SearchResultViewModel = hiltViewModel()
) {
    val keyword = rememberSaveable { mutableStateOf(arg ?: "") }
    val state = viewModel.searchResultState.collectAsState().value
    val pagingItems = viewModel.searchResult.collectAsLazyPagingItems()

    Spacer(modifier = Modifier.height(2.dp))
    SearchBox(
        value = keyword.value,
        onChangeValue = { keyword.value = it }
    )
    Spacer(modifier = Modifier.height(16.dp))
    SearchResultList(
        state = state,
        books = pagingItems,
        onClickItem = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBox(
    value: String,
    onChangeValue: (String) -> Unit
) {
    BasicTextField(
        value = value,
        onValueChange = onChangeValue,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        singleLine = true,
        decorationBox = @Composable { innerTextField ->
            TextFieldDefaults.TextFieldDecorationBox(
                value = value,
                innerTextField = innerTextField,
                enabled = true,
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = MaterialTheme.colorScheme.outline,
                    focusedBorderColor = MaterialTheme.colorScheme.outline,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                ),
                visualTransformation = VisualTransformation.None,
                interactionSource = remember { MutableInteractionSource() }
            )
        }
    )
}

@Composable
fun SearchResultList(
    state: SearchResultState,
    books: LazyPagingItems<BookInfo>,
    onClickItem: (BookInfo) -> Unit
) {
    LazyColumn {
        items(
            items = books,
            key = { it.id }
        ) { book ->
            SearchResultItem(
                info = book,
                onClickItem = onClickItem
            )
        }
    }
}

@Composable
fun SearchResultItem(
    info: BookInfo?,
    onClickItem: (BookInfo) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        AsyncImage(
            model = info?.volumeInfo?.images?.imageUrl,
            placeholder = painterResource(id = R.drawable.ic_not_found_image),
            error = painterResource(id = R.drawable.ic_not_found_image),
            fallback = painterResource(id = R.drawable.ic_not_found_image),
            contentDescription = null,
            modifier = Modifier
                .width(150.dp)
                .height(200.dp)
        )

        info?.let { SearchResultItemBody(info = it) }
    }
}

@Composable
fun SearchResultItemBody(
    info: BookInfo
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(text = info.volumeInfo.title)
        Text(text = info.volumeInfo.author)
        StarsRating(
            rateAverage = info.volumeInfo.averageReviewRate,
            totalAverage = info.volumeInfo.totalReviewCount
        )
        Text(text = info.volumeInfo.description, maxLines = 3)
        Text(text = stringResource(
            id = com.example.core_design.R.string.price,
            info.saleInfo.listPrice.price
        ))
    }
}

@Composable
fun StarsRating(
    rateAverage: Int,
    totalAverage: Int
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        modifier = Modifier
            .width(16.dp)
            .height(16.dp)
    ) {
        repeat(5) {
            Image(
                painter = painterResource(id = if (rateAverage > it) drawable.ic_review_star else drawable.ic_not_review),
                modifier = Modifier.fillMaxSize(),
                contentDescription = null
            )
        }
        Text(
            text = totalAverage.toString(),
            modifier = Modifier.fillMaxSize()
        )
    }
}