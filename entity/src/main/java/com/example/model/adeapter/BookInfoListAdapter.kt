package com.example.model.adeapter

import com.example.model.*

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

// APIから返ったレスポンスの中身のimageUrlがhttpなのでhttpsに置換する
fun updateBookInfo(books: List<BookInfo>): BookInfoList =
    BookInfoList(
        books.map {
            BookInfo(
                id = it.id,
                bookInfo = BookDetail(
                    title = it.bookInfo.title,
                    authors = it.bookInfo.authors,
                    publisher = it.bookInfo.publisher,
                    publishedDate = it.bookInfo.publishedDate,
                    description = it.bookInfo.description,
                    pageCount = it.bookInfo.pageCount,
                    categories = it.bookInfo.categories,
                    averageRating = it.bookInfo.averageRating,
                    ratingCount = it.bookInfo.ratingCount,
                    images = ImageLinks(
                        thumbnail = it.bookInfo.images?.imageUrl?.replace("http:", "https:")
                    ),
                    language = it.bookInfo.language,
                    previewLink = it.bookInfo.previewLink,
                ),
                salesInfo = it.salesInfo,
                accessInfo = it.accessInfo
            )
        }
    )