package com.example.entity.adeapter

import com.example.entity.*

fun cacheBookInfoAdapter(cache: List<CacheBookInfo>) =
        BookInfoListResponse(
            items = cache.map {
                BookInfo(
                    id = it.id,
                    bookInfo = VolumeInfo(
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
                        isFavorite = it.isFavorite
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