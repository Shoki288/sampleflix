package com.example.search_repository.usecase

import com.example.entity.BookInfoListResponse
import com.example.search_repository.SearchBookRepository
import retrofit2.Response
import javax.inject.Inject

class SearchBookUseCase @Inject constructor(
    private val repository: SearchBookRepository
) {
    suspend fun searchBook(keyword: String): Response<BookInfoListResponse> =
        repository.searchBooks(keyword = keyword)
}