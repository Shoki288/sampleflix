package com.example.entity

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

data class BookInfoList(
    val items: List<BookInfo>
)

@Parcelize
data class BookInfo(
    val id: String,
    @Json(name = "volumeInfo")
    val bookInfo: VolumeInfo,
    @Json(name ="saleInfo")
    val salesInfo: SaleInfo,
    val accessInfo: AccessInfo?
): Parcelable

@Parcelize
data class VolumeInfo(
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
): Parcelable {
    val averageReviewRate: Int
        get() = pageCount % 6
    val totalReviewCount: Int
        get() = pageCount
}

@Parcelize
data class ImageLinks(
    @Json(name = "thumbnail")
    private val thumbnail: String?
): Parcelable {
    val imageUrl: String
        get() = thumbnail?.replace("http:", "https:") ?: ""
}

@Parcelize
data class SaleInfo(
    val listPrice: Price?
): Parcelable

@Parcelize
data class Price(
    @Json(name = "amount")
    val price: Int
): Parcelable

@Parcelize
data class AccessInfo(
    val downloadAccess: DownloadAccess?
): Parcelable

@Parcelize
data class DownloadAccess(
    val message: String?
): Parcelable