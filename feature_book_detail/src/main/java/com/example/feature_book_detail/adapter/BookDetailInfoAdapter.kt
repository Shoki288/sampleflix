package com.example.feature_book_detail.adapter

import com.example.entity.BookInfo
import com.example.feature_book_detail.BookDetailInfo

fun convertBookInfoToBookDetailInfo(volumeInfo: BookInfo): BookDetailInfo =
    BookDetailInfo(
        title = volumeInfo.bookInfo.title,
        authors = volumeInfo.bookInfo.author.ifEmpty { "不明" },
        publisher = volumeInfo.bookInfo.publisher,
        publishedDate = volumeInfo.bookInfo.publishedDate,
        description = volumeInfo.bookInfo.description,
        pageCount = volumeInfo.bookInfo.pageCount,
        categories = volumeInfo.bookInfo.categories.joinToString().ifEmpty { "未定" },
        image = volumeInfo.bookInfo.images.imageUrl,
        language = volumeInfo.bookInfo.language,
        previewLink = volumeInfo.bookInfo.previewLink,
        averageReviewRate = volumeInfo.bookInfo.averageReviewRate,
        totalReviewCount = volumeInfo.bookInfo.totalReviewCount,
        price = volumeInfo.salesInfo.listPrice.price,
    )