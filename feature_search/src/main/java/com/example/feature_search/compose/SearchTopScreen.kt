package com.example.feature_search.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.feature_search.SearchViewModel
import com.example.feature_search.compose.widget.CategoryList
import com.example.feature_search.compose.widget.SearchBox

@Composable
fun SearchTopScreen(
    onSearch: (String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val keyword = rememberSaveable { mutableStateOf("") }

    Column {
        Spacer(modifier = Modifier.height(8.dp))
        SearchBox(
            keyword = keyword.value,
            onChangeValue = { keyword.value = it },
            onClickEnter = { onSearch(keyword.value) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        CategoryList(
            categories = viewModel.categories.collectAsState().value,
            onClickCategory = onSearch
        )
    }
}