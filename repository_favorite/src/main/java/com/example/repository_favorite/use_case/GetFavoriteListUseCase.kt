package com.example.repository_favorite.use_case

import com.example.entity.CacheBookInfo
import com.example.repository_favorite.FavoriteRepository
import javax.inject.Inject

class GetFavoriteListUseCase @Inject constructor(
    private val repository: FavoriteRepository,
) {
    suspend fun fetchFavoriteList(): List<CacheBookInfo> = repository.fetchFavoriteList()
}