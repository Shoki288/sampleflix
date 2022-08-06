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
    val authors: List<String> = emptyList(),
    val publisher: String?,
    val publishedDate: String?,  // YYYY-MM-ddで返る
    val description: String = "",
    val pageCount: Int = 0,
    val categories: List<String> = emptyList(),
    private val averageRating: Int?,
    private val ratingCount: Int = 0,
    @Json(name = "imageLinks")
    val images: ImageLinks?,
    val language: String,
    val previewLink: String
) {
    val averageReviewRate: Int = pageCount % 6
    val totalReviewCount: Int = pageCount
}

data class ImageLinks(
    @Json(name = "thumbnail")
    private val thumbnail: String?
) {
    val imageUrl = thumbnail?.replace("http:", "https:") ?: ""
}

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