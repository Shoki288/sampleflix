package com.example.feature_search_result.adapter

import com.example.feature_search_result.SearchResultAdapter.SearchResult
import com.example.model.BookInfo

fun convertSearchResultToBookInfo(bookInfoList: List<BookInfo>) =
    bookInfoList.map {
        SearchResult(
            imgUrl =it.bookInfo.images?.imageUrl ?: "",
            title =it.bookInfo.title,
            price =it.salesInfo.listPrice?.price ?: 0,
            description =it.bookInfo.description,
            author =it.bookInfo.authors.joinToString(),
            publisher =it.bookInfo.publisher,
            reviewTotalResult =it.bookInfo.totalReviewCount,
            reviewAverageResult =it.bookInfo.averageReviewRate
        )
    }