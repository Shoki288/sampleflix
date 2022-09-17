package com.example.entity.adeapter

import com.example.entity.*

fun convertBookInfoResponseToCacheBookInfoList(response: BookInfoListResponse): List<CacheBookInfo> =
    response.items.map { convertBookInfoToCacheBookInfo(it) }

fun convertBookInfoToCacheBookInfo(bookInfo: BookInfo): CacheBookInfo =
    CacheBookInfo(
        id = bookInfo.id,
        title = bookInfo.bookInfo.title,
        authors = bookInfo.bookInfo.author,
        publisher = bookInfo.bookInfo.publisher,
        publishedDate = bookInfo.bookInfo.publishedDate,
        description = bookInfo.bookInfo.description,
        pageCount = bookInfo.bookInfo.pageCount,
        categories = bookInfo.bookInfo.categories.joinToString(),
        averageRating = bookInfo.bookInfo.averageReviewRate,
        ratingCount = bookInfo.bookInfo.totalReviewCount,
        image = bookInfo.bookInfo.images.imageUrl,
        language = bookInfo.bookInfo.language,
        previewLink = bookInfo.bookInfo.previewLink,
        price = bookInfo.salesInfo.listPrice.price,
        isFavorite = bookInfo.bookInfo.isFavorite
    )

// APIから返ったレスポンスの中身のimageUrlがhttpなのでhttpsに置換する
fun modifyBookInfo(books: List<BookInfo>): BookInfoListResponse =
    BookInfoListResponse(
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
                        thumbnail = it.bookInfo.images.imageUrl
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