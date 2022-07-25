package com.example.model.adeapter

import com.example.model.*

fun cacheBookInfoAdapter(cache: List<CacheBookInfo>) =
        BookInfoList(
            items = cache.map {
                BookInfo(
                    id = it.id,
                    bookInfo = BookDetail(
                        title = it.title,
                        authors = it.authors.split(","),
                        publisher = it.publisher,
                        publishedDate = it.publishedDate,
                        description = it.description,
                        pageCount = it.pageCount,
                        categories = it.categories.split(","),
                        averageRating = it.averageRating,
                        ratingCount = it.ratingCount,
                        images = ImageLinks(
                            thumbnail = it.image
                        ),
                        language = it.language,
                        previewLink = it.previewLink,
                    ),
                    salesInfo = SaleInfo(
                        listPrice = Price(
                            price = it.price
                        )
                    ),
                    accessInfo = null,
                )
            }
        )