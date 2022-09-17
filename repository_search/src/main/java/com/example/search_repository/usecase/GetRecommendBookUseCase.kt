package com.example.search_repository.usecase

import com.example.entity.BookInfoListResponse
import com.example.extension.api.*
import com.example.repository_favorite.use_case.GetFavoriteListUseCase
import com.example.search_repository.SearchBookRepository
import javax.inject.Inject

class GetRecommendBookUseCase @Inject constructor(
    private val repository: SearchBookRepository,
    private val getFavoriteListUseCase: GetFavoriteListUseCase,
) {
    suspend fun searchBookInit(): ApiResult<BookInfoListResponse> {
        val favoriteIds = getFavoriteListUseCase.fetchFavoriteList().map { it.id }
        return when(val books = repository.searchBooksInit()) {
            is Success -> {
                books.run { Success(data.also { it.items.filter { item -> favoriteIds.contains(item.id) } }) }
            }
            else -> books
        }
    }
}