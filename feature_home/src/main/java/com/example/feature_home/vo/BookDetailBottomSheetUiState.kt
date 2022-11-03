package com.example.feature_home.vo

import com.example.entity.BookInfo

sealed class BookDetailBottomSheetUiState {
    object Loading: BookDetailBottomSheetUiState()
    data class Success(val book: BookInfo): BookDetailBottomSheetUiState()
}