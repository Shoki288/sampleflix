package com.example.core_unit_test.fixture

import com.example.entity.*

fun createBookInfoList(
    list: List<BookInfo> = emptyList()
) = BookInfoListResponse(list)

fun createBookInfo(
    id: String = "id",
    bookInfo: VolumeInfo = createVolumeInfo(),
    salesInfo: SaleInfo = createSaleInfo(),
    accessInfo: AccessInfo? = createAccessInfo(),
) = BookInfo(
    id = id,
    volumeInfo = bookInfo,
    saleInfo = salesInfo,
    accessInfo = accessInfo,
)

fun createVolumeInfo(
    title: String = "title",
    authors: List<String> = emptyList(),
    publisher: String = "publisher",
    publishedDate: String = "publishedDate",  // YYYY-MM-ddで返る
    description: String = "description",
    pageCount: Int = 0,
    categories: List<String> = emptyList(),
    averageRating: Int? = 0,
    ratingCount: Int = 0,
    images: ImageLinks = createImageLinks(),
    language: String = "language",
    previewLink: String = "previewLink",
    isFavorite: Boolean = false
) = VolumeInfo(
    title = title,
    authors = authors,
    publisher = publisher,
    publishedDate = publishedDate,
    description = description,
    pageCount = pageCount,
    categories = categories,
    averageRating = averageRating,
    ratingCount = ratingCount,
    images = images,
    language = language,
    previewLink = previewLink,
    isFavorite = isFavorite,

    )

fun createImageLinks(
    thumbnail: String = "thumbnail"
) = ImageLinks(
    thumbnail = thumbnail
)

fun createSaleInfo(
    listPrice: Price = createPrice()
) = SaleInfo(
    listPrice = listPrice
)

fun createPrice(
    price: Int = 0
) = Price(
    price = price
)

fun createAccessInfo(
    downloadAccess: DownloadAccess? = createDownloadAccess()
) = AccessInfo(
    downloadAccess = downloadAccess
)

fun createDownloadAccess(
    message: String? = "message"
) = DownloadAccess(
    message = message
)