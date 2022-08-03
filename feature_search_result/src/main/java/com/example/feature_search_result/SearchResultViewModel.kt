package com.example.feature_search_result

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchResultViewModel: ViewModel() {
    private val _searchResult = MutableStateFlow<String>("")
    val searchResult = _searchResult.asStateFlow()
}