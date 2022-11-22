package com.example.entity.adeapter

import com.example.entity.*

fun convertBookInfoResponseToCacheBookInfoList(response: BookInfoListResponse): List<CacheBookInfo> =
    response.items.map { convertBookInfoToCacheBookInfo(it) }

fun convertBookInfoToCacheBookInfo(bookInfo: BookInfo): CacheBookInfo =
    CacheBookInfo(
        id = bookInfo.id,
        title = bookInfo.volumeInfo.title,
        authors = bookInfo.volumeInfo.author,
        publisher = bookInfo.volumeInfo.publisher,
        publishedDate = bookInfo.volumeInfo.publishedDate,
        description = bookInfo.volumeInfo.description,
        pageCount = bookInfo.volumeInfo.pageCount,
        categories = bookInfo.volumeInfo.categories.joinToString(),
        averageRating = bookInfo.volumeInfo.averageReviewRate,
        ratingCount = bookInfo.volumeInfo.totalReviewCount,
        image = bookInfo.volumeInfo.images.imageUrl,
        language = bookInfo.volumeInfo.language,
        previewLink = bookInfo.volumeInfo.previewLink,
        price = bookInfo.saleInfo.listPrice.price,
        isFavorite = bookInfo.volumeInfo.isFavorite
    )

// APIから返ったレスポンスの中身のimageUrlがhttpなのでhttpsに置換する
fun modifyBookInfo(books: List<BookInfo>): BookInfoListResponse =
    BookInfoListResponse(
        books.map {
            BookInfo(
                id = it.id,
                volumeInfo = VolumeInfo(
                    title = it.volumeInfo.title,
                    authors = it.volumeInfo.author.split(","),
                    publisher = it.volumeInfo.publisher,
                    publishedDate = it.volumeInfo.publishedDate,
                    description = it.volumeInfo.description,
                    pageCount = it.volumeInfo.pageCount,
                    categories = it.volumeInfo.categories,
                    averageRating = it.volumeInfo.averageReviewRate,
                    ratingCount = it.volumeInfo.totalReviewCount,
                    images = ImageLinks(
                        thumbnail = it.volumeInfo.images.imageUrl
                    ),
                    language = it.volumeInfo.language,
                    previewLink = it.volumeInfo.previewLink,
                    isFavorite = false
                ),
                saleInfo = it.saleInfo,
                accessInfo = it.accessInfo
            )
        }
    )