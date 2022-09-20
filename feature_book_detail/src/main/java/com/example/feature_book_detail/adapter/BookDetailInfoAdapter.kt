package com.example.feature_book_detail.adapter

import com.example.entity.BookInfo
import com.example.feature_book_detail.BookDetailInfo

fun convertBookInfoToBookDetailInfo(volumeInfo: BookInfo): BookDetailInfo =
    BookDetailInfo(
        title = volumeInfo.volumeInfo.title,
        authors = volumeInfo.volumeInfo.author.ifEmpty { "不明" },
        publisher = volumeInfo.volumeInfo.publisher,
        publishedDate = volumeInfo.volumeInfo.publishedDate,
        description = volumeInfo.volumeInfo.description,
        pageCount = volumeInfo.volumeInfo.pageCount,
        categories = volumeInfo.volumeInfo.categories.joinToString().ifEmpty { "未定" },
        image = volumeInfo.volumeInfo.images.imageUrl,
        language = volumeInfo.volumeInfo.language,
        previewLink = volumeInfo.volumeInfo.previewLink,
        averageReviewRate = volumeInfo.volumeInfo.averageReviewRate,
        totalReviewCount = volumeInfo.volumeInfo.totalReviewCount,
        price = volumeInfo.saleInfo.listPrice.price,
    )