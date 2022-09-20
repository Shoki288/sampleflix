package com.example.search_repository.usecase

import com.example.entity.BookInfo
import com.example.entity.adeapter.convertBookInfoToCacheBookInfo
import com.example.search_repository.SearchBookRepository
import javax.inject.Inject

class UpdateRecommendFavoriteStateUseCase @Inject constructor(
    private val repository: SearchBookRepository
) {
    suspend fun updateFavoriteState(bookInfo: BookInfo) =
        repository.updateFavoriteState(convertBookInfoToCacheBookInfo(bookInfo))
}