package com.example.core_cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core_cache.dao.BookInfoDao
import com.example.entity.CacheBookInfo

@Database(entities = [CacheBookInfo::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun bookInfoDao(): BookInfoDao
}