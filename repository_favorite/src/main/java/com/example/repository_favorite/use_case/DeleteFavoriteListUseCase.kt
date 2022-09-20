package com.example.repository_favorite.use_case

import com.example.entity.BookInfo
import com.example.entity.adeapter.convertBookInfoToCacheBookInfo
import com.example.repository_favorite.FavoriteRepository
import javax.inject.Inject

class DeleteFavoriteListUseCase @Inject constructor(
    private val repository: FavoriteRepository,
) {
    suspend fun deleteFavoriteItem(bookInfo: BookInfo) =
        repository.deleteFavoriteItem(convertBookInfoToCacheBookInfo(bookInfo))
}