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
    val authors: List<String>,
    val publisher: String,
    val publishedDate: String,  // YYYY-MM-ddで返る
    val description: String,
    val pageCount: Int,
    val categories: List<String>,
    val averageRating: Double,
    val ratingCount: Int,
    @Json(name = "imageLinks")
    val images: ImageLinks?,
    val language: String,
    val previewLink: String
)

data class ImageLinks(
    @Json(name = "smallThumbnail")
    val smallImg: String?,
    @Json(name = "thumbnail")
    val mediumImg: String?
)

data class SaleInfo(
    val listPrice: Price?
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