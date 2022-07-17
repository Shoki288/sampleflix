package com.example.search_repository.usecase

import com.example.model.BookInfoList
import com.example.search_repository.SearchBookRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import retrofit2.Response
import javax.inject.Inject

class SearchBookUseCase @Inject constructor(
    private val repository: SearchBookRepository
) {
    suspend fun searchBookInit(): BookInfoList =
        coroutineScope {
            val bookAndroid = async { searchBookAndroid() }
            val bookFF = async { searchBookFF() }

            BookInfoList(bookAndroid.await().body()?.items.orEmpty() + bookFF.await().body()?.items.orEmpty())
        }

    private fun searchBookAndroid(): Response<BookInfoList> =
        repository.searchBooks(keyword = "android", maxResultSize = 40)

    private fun searchBookFF(): Response<BookInfoList> =
        repository.searchBooks(keyword = "FF14", maxResultSize = 40)

    fun searchBook(keyword: String): Response<BookInfoList> =
        repository.searchBooks(keyword = keyword)
}