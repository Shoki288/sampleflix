package com.example.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CacheBookInfo(
    @PrimaryKey val id: String,
    @ColumnInfo val title: String,
    @ColumnInfo val authors: String,        // カンマ区切り
    @ColumnInfo val publisher: String,
    @ColumnInfo val publishedDate: String,  // YYYY-MM-dd
    @ColumnInfo val description: String,
    @ColumnInfo val pageCount: Int,
    @ColumnInfo val categories: String,     // カンマ区切り
    @ColumnInfo val averageRating: Int?,
    @ColumnInfo val ratingCount: Int,
    @ColumnInfo val image: String,
    @ColumnInfo val language: String,
    @ColumnInfo val previewLink: String,
    @ColumnInfo val price: Int,
    @ColumnInfo val isFavorite: Boolean
)