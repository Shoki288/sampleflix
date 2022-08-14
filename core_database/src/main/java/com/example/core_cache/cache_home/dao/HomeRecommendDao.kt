package com.example.core_cache.cache_home.dao

import androidx.room.*
import com.example.core_cache.DBTransaction
import com.example.entity.CacheBookInfo

@Dao
abstract class HomeRecommendDao(override val database: RoomDatabase): DBTransaction {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertAll(books: List<CacheBookInfo>)

    @Query("SELECT * FROM cachebookinfo")
    abstract suspend fun getAll(): List<CacheBookInfo>

    @Query("SELECT categories FROM cachebookinfo")
    abstract suspend fun getAllBooksCategory(): List<String>
}