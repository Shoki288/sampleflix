package com.example.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteUpdate(
    @PrimaryKey val id: String,
    @ColumnInfo(name ="isFavorite") val isFavorite: Boolean,
)