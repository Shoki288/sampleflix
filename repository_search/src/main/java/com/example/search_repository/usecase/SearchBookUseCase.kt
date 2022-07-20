package com.example.search_repository.usecase

import com.example.extension.api.ApiResult
import com.example.model.BookInfoList
import com.example.search_repository.SearchBookRepository
import retrofit2.Response
import javax.inject.Inject

class SearchBookUseCase @Inject constructor(
    private val repository: SearchBookRepository
) {
    // TODO repositoryの処理を横流しする
    suspend fun searchBookInit(): ApiResult<BookInfoList> = repository.searchBooksInit()
    suspend fun searchBook(keyword: String): Response<BookInfoList> =
        repository.searchBooks(keyword = keyword)
}