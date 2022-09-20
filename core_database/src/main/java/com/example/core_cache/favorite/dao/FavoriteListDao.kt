package com.example.core_cache.favorite.dao

import androidx.room.*
import com.example.core_cache.DBTransaction
import com.example.entity.CacheBookInfo

@Dao
abstract class FavoriteListDao(override val database: RoomDatabase): DBTransaction {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertFavoriteItem(bookInfo: CacheBookInfo)

    @Query("SELECT * FROM cachebookinfo")
    abstract suspend fun getAll(): List<CacheBookInfo>

    @Delete
    abstract suspend fun deleteFavoriteItem(bookInfo: CacheBookInfo)
}