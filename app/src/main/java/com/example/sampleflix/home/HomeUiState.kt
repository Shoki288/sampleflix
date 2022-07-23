package com.example.sampleflix.home

import com.example.model.BookInfo

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val books: List<BookInfo>) : HomeUiState()
    data class ApiError(val message: String) : HomeUiState()
}