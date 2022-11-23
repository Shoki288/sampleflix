package com.example.repository_favorite.use_case

import com.example.entity.BookInfo
import com.example.entity.adeapter.cacheBookInfoAdapter
import com.example.repository_favorite.FavoriteRepository
import javax.inject.Inject

class GetFavoriteListUseCase @Inject constructor(
    private val repository: FavoriteRepository,
) {
    suspend fun fetchFavoriteList(): List<BookInfo> = cacheBookInfoAdapter(repository.fetchFavoriteList()).items
}