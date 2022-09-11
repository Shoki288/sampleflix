package com.example.repository_favorite.use_case

import com.example.entity.CacheBookInfo
import com.example.repository_favorite.FavoriteRepository
import javax.inject.Inject

class UpdateFavoriteListUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    suspend fun updateFavoriteList(bookInfo: CacheBookInfo): Any =
        repository.updateFavoriteList(bookInfo)
}