package com.example.feature_favorite.android_view

import com.example.entity.CacheBookInfo

sealed class FavoriteListUiState {
    object Loading: FavoriteListUiState()
    data class Success(val bookInfoList: List<CacheBookInfo>): FavoriteListUiState()
    sealed class Error(val message: String): FavoriteListUiState() {
        object EmptyList: Error("お気に入り登録されている商品が見つかりませんでした。")
        object Exception: Error("通信環境が良くないようです。時間をおいて再度お試しください")
    }
}