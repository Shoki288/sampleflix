package com.example.core_cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.model.CacheBookInfo

@Dao
interface BookInfoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(books: List<CacheBookInfo>)

    @Query("SELECT * FROM cachebookinfo")
    suspend fun getAll(): List<CacheBookInfo>

    @Query("SELECT categories FROM cachebookinfo")
    suspend fun getAllBooksCategory(): List<String>
}