package com.example.core_cache

import androidx.room.*
import com.example.core_cache.dao.BookInfoDao
import com.example.entity.CacheBookInfo

@Database(entities = [CacheBookInfo::class], version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun bookInfoDao(): BookInfoDao
}