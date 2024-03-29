package com.example.feature_search.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core_design.theme.AppTheme
import com.example.feature_search.SearchViewModel
import com.example.feature_search.compose.widget.CategoryItem
import com.example.feature_search.compose.widget.SearchBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopRoot(
    onSearch: (String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val keyword = rememberSaveable { mutableStateOf("") }
    val categories by viewModel.categories.collectAsState()

    AppTheme {
        Scaffold { paddingValues ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant).padding(paddingValues),
                contentPadding = PaddingValues(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                item(
                    span = { GridItemSpan(2) }
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
                // 検索ボックス
                item(
                    span = { GridItemSpan(2) }
                ) {
                    SearchBox(
                        keyword = keyword.value,
                        onChangeValue = { keyword.value = it },
                        onClickEnter = { onSearch(keyword.value) }
                    )
                }
                item(
                    span = { GridItemSpan(2) }
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                items(
                    count = categories.size,
                ) { index ->
                    CategoryItem(
                        category = categories[index],
                        onClickItem = onSearch
                    )
                }
            }
        }
    }

}