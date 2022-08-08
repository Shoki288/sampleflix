package com.example.feature_search_result.adapter

import com.example.feature_search_result.SearchResultAdapter.SearchResult
import com.example.entity.BookInfo

fun convertSearchResultToBookInfo(bookInfoList: List<BookInfo>) =
    bookInfoList.map {
        SearchResult(
            id = it.id,
            imgUrl =it.bookInfo.images?.imageUrl ?: "",
            title =it.bookInfo.title,
            price =it.salesInfo.listPrice.price,
            description =it.bookInfo.description,
            author =it.bookInfo.authors.joinToString().ifEmpty { "不明" },
            publisher =it.bookInfo.publisher ?: "不明",
            reviewTotalResult =it.bookInfo.totalReviewCount,
            reviewAverageResult =it.bookInfo.averageReviewRate
        )
    }