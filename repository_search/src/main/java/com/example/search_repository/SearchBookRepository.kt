package com.example.search_repository

import com.example.core_retrofit.SearchBooksService
import com.example.model.BookInfoList
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import retrofit2.Response
import javax.inject.Inject

class SearchBookRepository @Inject constructor(
    private val service: SearchBooksService
) {
    suspend fun searchBooksInit() {
        coroutineScope {
            val responseAndroid = async { searchBookAndroid() }
            val responseFF = async { searchBookFF() }
            // TODO db呼び出す。cacheもここでやっちゃう
            // TODO cacheなかった場合, zipでAPI呼ぶ。ApiResult<T>を返す
        }
    }

    private suspend fun searchBookAndroid(): Response<BookInfoList> = searchBooks(keyword = "android", maxResultSize = 40)
    private suspend fun searchBookFF(): Response<BookInfoList> = searchBooks(keyword = "FF14", maxResultSize = 40)

    suspend fun searchBooks(keyword: String, maxResultSize: Int? = null): Response<BookInfoList> =
        service.searchBooks(keyword, maxResultSize)
}