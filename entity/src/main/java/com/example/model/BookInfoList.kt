package com.example.model

import com.squareup.moshi.Json

data class BookInfoList(
    val items: List<BookInfo>
)

data class BookInfo(
    val id: String,
    @Json(name = "volumeInfo")
    val bookInfo: BookDetail,
    @Json(name ="saleInfo")
    val salesInfo: SaleInfo,
    val accessInfo: AccessInfo?
)

// Success
data class BookDetail(
    val title: String,
    @Json(name = "imageLinks")
    val images: ImageLinks?
)

data class ImageLinks(
    @Json(name = "smallThumbnail")
    val smallImg: String?,
    @Json(name = "thumbnail")
    val mediumImg: String?
)

data class SaleInfo(
    val listPrice: Price?,
    val retailPrice: Price?
)

data class Price(
    @Json(name = "amount")
    val price: Int
)

// Error
data class AccessInfo(
    val downloadAccess: DownloadAccess?
)

data class DownloadAccess(
    val message: String?
)