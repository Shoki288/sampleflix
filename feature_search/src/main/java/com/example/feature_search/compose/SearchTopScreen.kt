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
import androidx.navigation.NavController
import com.example.feature_search.SearchViewModel
import com.example.feature_search.compose.widget.CategoryList
import com.example.feature_search.compose.widget.SearchBox

@Composable
fun SearchTopScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchWord = rememberSaveable { mutableStateOf("") }

    Column {
        Spacer(modifier = Modifier.height(2.dp))
        SearchBox(
            value = searchWord.value,
            onChangeValue = { searchWord.value = it }
        )
        Spacer(modifier = Modifier.height(16.dp))
        CategoryList(
            categories = viewModel.categories.collectAsState().value,
            onClickCategory = {}
        )
    }
}