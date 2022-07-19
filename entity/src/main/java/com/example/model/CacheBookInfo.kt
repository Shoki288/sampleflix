package com.example.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CacheBookInfo(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "authors") val authors: List<String>,
    @ColumnInfo(name = "publisher") val publisher: String,
    @ColumnInfo(name = "publishedDate") val publishedDate: String,  // YYYY-MM-dd
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "pageCount") val pageCount: Int,
    @ColumnInfo(name = "categories") val categories: List<String>,
    @ColumnInfo(name = "averageRating") val averageRating: Double,
    @ColumnInfo(name = "ratingCount") val ratingCount: Int,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "language") val language: String,
    @ColumnInfo(name = "previewLink") val previewLink: String
)