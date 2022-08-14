package com.example.repository_favorite

import com.example.core_cache.cache_search_result.dao.FavoriteListDao
import com.example.entity.CacheBookInfo
import javax.inject.Inject

class FavoriteRepository @Inject constructor(
    private val dao: FavoriteListDao
) {
    suspend fun fetchFavoriteList(): List<CacheBookInfo> = dao.getAll()
}