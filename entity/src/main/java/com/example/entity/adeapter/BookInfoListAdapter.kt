package com.example.entity.adeapter

import com.example.entity.*

fun bookInfoListAdapter(response: BookInfoList) =
    response.items.map {
        CacheBookInfo(
            id = it.id,
            title = it.bookInfo.title,
            authors = it.bookInfo.author,
            publisher = it.bookInfo.publisher,
            publishedDate = it.bookInfo.publishedDate,
            description = it.bookInfo.description,
            pageCount = it.bookInfo.pageCount,
            categories = it.bookInfo.categories.joinToString(),
            averageRating = it.bookInfo.averageReviewRate,
            ratingCount = it.bookInfo.totalReviewCount,
            image = it.bookInfo.images?.imageUrl ?: "",
            language = it.bookInfo.language,
            previewLink = it.bookInfo.previewLink,
            price = it.salesInfo.listPrice.price,
            isFavorite = it.bookInfo.isFavorite
        )
    }

// APIから返ったレスポンスの中身のimageUrlがhttpなのでhttpsに置換する
fun updateBookInfo(books: List<BookInfo>): BookInfoList =
    BookInfoList(
        books.map {
            BookInfo(
                id = it.id,
                bookInfo = VolumeInfo(
                    title = it.bookInfo.title,
                    authors = it.bookInfo.author.split(","),
                    publisher = it.bookInfo.publisher,
                    publishedDate = it.bookInfo.publishedDate,
                    description = it.bookInfo.description,
                    pageCount = it.bookInfo.pageCount,
                    categories = it.bookInfo.categories,
                    averageRating = it.bookInfo.averageReviewRate,
                    ratingCount = it.bookInfo.totalReviewCount,
                    images = ImageLinks(
                        // TODO 正規表現のほうが早い気がする
                        thumbnail = it.bookInfo.images?.imageUrl?.replace("http:", "https:")
                    ),
                    language = it.bookInfo.language,
                    previewLink = it.bookInfo.previewLink,
                    isFavorite = false
                ),
                salesInfo = it.salesInfo,
                accessInfo = it.accessInfo
            )
        }
    )