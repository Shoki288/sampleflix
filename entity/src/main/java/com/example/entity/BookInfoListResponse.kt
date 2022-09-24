package com.example.entity

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookInfoListResponse(
    val items: List<BookInfo>
): Parcelable

@Parcelize
data class BookInfo(
    val id: String,
    val volumeInfo: VolumeInfo,
    val saleInfo: SaleInfo,
    val accessInfo: AccessInfo?
): Parcelable

@Parcelize
data class VolumeInfo(
    val title: String,
    private val authors: List<String> = emptyList(),
    val publisher: String = "",
    val publishedDate: String = "2022",  // YYYY-MM-ddで返る
    val description: String = "",
    val pageCount: Int = 0,
    val categories: List<String> = emptyList(),
    private val averageRating: Int?,
    private val ratingCount: Int = 0,
    @Json(name = "imageLinks")
    val images: ImageLinks = ImageLinks(),
    val language: String,
    val previewLink: String,
    val isFavorite: Boolean = false
): Parcelable {
    // TODO non-nullにして他画面でも表記統一させたい
    val averageReviewRate: Int
        get() = pageCount % 6
    val totalReviewCount: Int
        get() = pageCount
    val author: String
        get() = authors.joinToString().ifEmpty { "不明" }
}

@Parcelize
data class ImageLinks(
    @Json(name = "thumbnail")
    private val thumbnail: String = ""
): Parcelable {
    val imageUrl: String
        get() = thumbnail.replace("http:", "https:")
}

@Parcelize
data class SaleInfo(
    val listPrice: Price = Price()
): Parcelable

@Parcelize
data class Price(
    @Json(name = "amount")
    val price: Int = 0
): Parcelable

@Parcelize
data class AccessInfo(
    val downloadAccess: DownloadAccess?
): Parcelable

@Parcelize
data class DownloadAccess(
    val message: String?
): Parcelable