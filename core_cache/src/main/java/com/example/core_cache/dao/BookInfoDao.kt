package com.example.core_cache.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.model.CacheBookInfo

@Dao
interface BookInfoDao {
    @Query("SELECT * FROM cachebookinfo")
    fun getAll(): List<CacheBookInfo>
}