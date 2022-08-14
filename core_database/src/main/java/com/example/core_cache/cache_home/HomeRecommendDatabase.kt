package com.example.core_cache.cache_home

import androidx.room.*
import com.example.core_cache.cache_home.dao.HomeRecommendDao
import com.example.entity.CacheBookInfo

@Database(entities = [CacheBookInfo::class], version = 2, exportSchema = false)
abstract class HomeRecommendDatabase: RoomDatabase() {
    abstract fun homeRecommendDao(): HomeRecommendDao
}