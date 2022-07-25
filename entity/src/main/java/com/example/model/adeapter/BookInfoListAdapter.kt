package com.example.model.adeapter

import com.example.model.BookInfoList
import com.example.model.CacheBookInfo

fun bookInfoListAdapter(response: BookInfoList) =
    response.items.map {
        CacheBookInfo(
            id = it.id,
            title = it.bookInfo.title,
            authors = it.bookInfo.authors.joinToString(),
            publisher = it.bookInfo.publisher,
            publishedDate = it.bookInfo.publishedDate,
            description = it.bookInfo.description,
            pageCount = it.bookInfo.pageCount,
            categories = it.bookInfo.categories.joinToString(),
            averageRating = it.bookInfo.averageRating,
            ratingCount = it.bookInfo.ratingCount,
            image = it.bookInfo.images?.imageUrl ?: "",
            language = it.bookInfo.language,
            previewLink = it.bookInfo.previewLink,
            price = it.salesInfo.listPrice?.price ?: 0,
        )
    }