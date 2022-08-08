package com.example.feature_home

import com.example.entity.BookInfo

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val books: List<BookInfo>) : HomeUiState()
    data class ApiError(val message: String) : HomeUiState()
}