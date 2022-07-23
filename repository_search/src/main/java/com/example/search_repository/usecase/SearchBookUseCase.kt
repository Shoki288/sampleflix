package com.example.search_repository.usecase

import com.example.extension.api.Result
import com.example.model.BookInfoList
import com.example.search_repository.SearchBookRepository
import retrofit2.Response
import javax.inject.Inject

class SearchBookUseCase @Inject constructor(
    private val repository: SearchBookRepository
) {
    suspend fun searchBookInit(): Result<BookInfoList> = repository.searchBooksInit()
    suspend fun searchBook(keyword: String): Response<BookInfoList> =
        repository.searchBooks(keyword = keyword)
}