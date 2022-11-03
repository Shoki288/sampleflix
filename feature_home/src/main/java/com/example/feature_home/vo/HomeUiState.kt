package com.example.feature_home.vo

import com.example.entity.BookInfo

sealed class HomeUiState {
    object None: HomeUiState()
    object Loading : HomeUiState()
    data class Success(val books: List<BookInfo>) : HomeUiState()
    sealed class Error(open val message: String): HomeUiState() {
        data class ApiError(override val message: String) : Error(message)
        object NetworkError : Error("接続できませんでした。もう一度時間をおいて確認してください。")
    }
}