package com.example.core_cache.cache_search_result

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core_cache.cache_search_result.dao.FavoriteListDao
import com.example.entity.CacheBookInfo

@Database(entities = [CacheBookInfo::class], version = 1, exportSchema = false)
abstract class FavoriteListDatabase: RoomDatabase() {
    abstract fun favoriteListDao(): FavoriteListDao
}