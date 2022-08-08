package com.example.feature_book_detail

data class BookDetailInfoUiState(
    val loading: Boolean = false,
    val error: Boolean = false,
    val bookDetailInfo: BookDetailInfo = BookDetailInfo(),
)

data class BookDetailInfo(
    val title: String = "",
    val authors: String = "",
    val publisher: String = "",
    val publishedDate: String = "",
    val description: String = "",
    val pageCount: Int = 0,
    val categories: String = "",
    val image: String = "",
    val language: String = "",
    val previewLink: String = "",
    val averageReviewRate: Int = 0,
    val totalReviewCount: Int = 0,
    val price: Int = 0
)