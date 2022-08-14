package com.example.repository_favorite.use_case

import com.example.repository_favorite.FavoriteRepository
import javax.inject.Inject

class FavoriteListUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    suspend fun fetchFavoriteList() = repository.fetchFavoriteList()
}