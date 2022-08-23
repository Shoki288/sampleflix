package com.example.core_cache.favorite

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core_cache.favorite.dao.FavoriteListDao
import com.example.entity.CacheBookInfo

@Database(entities = [CacheBookInfo::class], version = 1, exportSchema = false)
abstract class FavoriteListDatabase: RoomDatabase() {
    abstract fun favoriteListDao(): FavoriteListDao
}