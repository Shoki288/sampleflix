package com.example.core_unit_test.fixture

import com.example.entity.CacheBookInfo

fun createCacheBookInfo(
    id: String = "id",
    title: String = "title",
    authors: String = "authors",
    publisher: String? = "publisher",
    publishedDate: String? = "publishedDate",
    description: String = "description",
    pageCount: Int = 0,
    categories: String = "categories",
    averageRating: Int? = 0,
    ratingCount: Int = 0,
    image: String = "image",
    language: String = "language",
    previewLink: String = "previewLink",
    price: Int = 0,
    isFavorite: Boolean = false,
) = CacheBookInfo(
    id = id,
    title = title,
    authors = authors,
    publisher = publisher,
    publishedDate = publishedDate,
    description = description,
    pageCount = pageCount,
    categories = categories,
    averageRating = averageRating,
    ratingCount = ratingCount,
    image = image,
    language = language,
    previewLink = previewLink,
    price = price,
    isFavorite = isFavorite,
)
