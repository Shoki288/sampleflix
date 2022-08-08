package com.example.feature_search_result

import com.example.entity.BookInfo

sealed class SearchResultUiState {
    object Loading: SearchResultUiState()
    data class Success(val results: List<BookInfo>): SearchResultUiState()
    data class Error(val message: String): SearchResultUiState()
}